package ru.capjack.kt.logging.js.gradle.compiler

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.js.backend.ast.JsNameRef
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral
import org.jetbrains.kotlin.js.translate.context.Namer
import org.jetbrains.kotlin.js.translate.context.TranslationContext
import org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension
import org.jetbrains.kotlin.js.translate.utils.BindingUtils
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtPureClassOrObject
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.resolve.calls.callUtil.getCall
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.types.KotlinType

class JsSyntheticTranslateExtension(
	private val messenger: MessageCollector
) : JsSyntheticTranslateExtension {
	
	private var fqNameLogger = FqName("ru.capjack.lib.kt.logging.Logger")
	private var fqNameLigging = FqName("ru.capjack.lib.kt.logging.Logging")
	
	private var fqNameGetLogger = FqName("ru.capjack.lib.kt.logging.Logging.getLogger")
	private var fqNameGetLoggerExt = FqName("ru.capjack.lib.kt.logging.getLogger")
	
	
	fun log(vararg a: Any?) {
		messenger.report(CompilerMessageSeverity.WARNING, a.joinToString(" "))
	}
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		declaration.declarations
			.filterIsInstance<KtProperty>()
			.forEach { processProperty(context, it) }
		
	}
	
	private fun processProperty(context: TranslationContext, property: KtProperty) {
		val bindingContext = context.bindingContext()
		
		if (BindingUtils.getPropertyDescriptor(bindingContext, property).type.constructor.declarationDescriptor?.fqNameSafe != fqNameLogger) {
			return
		}
		
		val initializer = property.initializer as? KtDotQualifiedExpression
			?: return
		
		val target = initializer.firstChild as? KtReferenceExpression
			?: return
		
		if (BindingUtils.getDescriptorForReferenceExpression(bindingContext, target)?.fqNameSafe != fqNameLigging) {
			return
		}
		
		val call = (initializer.lastChild as? KtCallExpression)?.getCall(bindingContext)
			?: return
		
		val calleeExpression = call.calleeExpression as? KtReferenceExpression
			?: return
		
		val fnName = BindingUtils.getDescriptorForReferenceExpression(bindingContext, calleeExpression)?.fqNameSafe
			?: return
		
		val type = when (fnName) {
			fqNameGetLogger    -> {
				if (call.valueArguments.size != 1) {
					return
				}
				BindingUtils.getTypeForExpression(bindingContext, call.valueArguments[0].getArgumentExpression() ?: return).arguments.let {
					if (it.size != 1) return
					it[0].type
				}
			}
			fqNameGetLoggerExt -> {
				if (call.typeArguments.size != 1) {
					return
				}
				
				BindingUtils.getTypeByReference(bindingContext, call.typeArguments[0].typeReference ?: return)
			}
			else               -> return
		}
		
		processType(context, type)
		
	}
	
	private val processedTypes = mutableSetOf<KotlinType>()
	
	private fun processType(context: TranslationContext, type: KotlinType) {
		
		type.constructor.declarationDescriptor?.also {
			if (processedTypes.add(type)) {
				context.addTopLevelStatement(
					JsAstUtils.assignment(
						JsNameRef("package", JsNameRef(Namer.METADATA, context.getInnerReference(it))),
						JsStringLiteral(it.fqNameSafe.parent().asString())
					).makeStmt()
				)
			}
		}
	}
}
