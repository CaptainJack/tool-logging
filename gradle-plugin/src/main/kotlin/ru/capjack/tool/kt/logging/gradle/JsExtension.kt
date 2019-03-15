package ru.capjack.tool.kt.logging.gradle

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.resolveClassByFqName
import org.jetbrains.kotlin.incremental.components.NoLookupLocation
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
import org.jetbrains.kotlin.psi.KtPureClassOrObject
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.psi.KtTreeVisitor
import org.jetbrains.kotlin.resolve.calls.callUtil.getType
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.types.KotlinType

class JsExtension : JsSyntheticTranslateExtension {
	
	private var fqNameLogger = FqName("ru.capjack.tool.kt.logging.Logger")
	private var fqNameGetLogger = FqName("ru.capjack.tool.kt.logging.Logging.getLogger")
	private var fqNameGetLoggerExt = FqName("ru.capjack.tool.kt.logging.getLogger")
	
	private val processedTypes = mutableSetOf<KotlinType>()
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		
		val loggerType = context.currentModule.resolveClassByFqName(fqNameLogger, NoLookupLocation.FROM_BACKEND)?.defaultType
			?: return
		
		val visitor = object : KtTreeVisitor<Unit>() {
			override fun visitCallExpression(expression: KtCallExpression, data: Unit): Void? {
				
				val type = (expression.calleeExpression as? KtReferenceExpression)?.let { referenceExpression ->
					val bindingContext = context.bindingContext()
					
					if (expression.getType(bindingContext) == loggerType) {
						
						when (BindingUtils.getDescriptorForReferenceExpression(bindingContext, referenceExpression)?.fqNameSafe) {
							fqNameGetLogger    -> {
								if (expression.valueArguments.size == 1 && expression.typeArguments.isEmpty()) {
									BindingUtils.getTypeForExpression(bindingContext, expression.valueArguments[0].getArgumentExpression()!!).arguments[0].type
								}
								else null
							}
							fqNameGetLoggerExt -> {
								if (expression.valueArguments.isEmpty() && expression.typeArguments.size == 1) {
									BindingUtils.getTypeByReference(bindingContext, expression.typeArguments[0].typeReference!!)
								}
								else null
							}
							else               -> null
						}
					}
					else null
				}
				
				if (type != null) {
					processType(context, type)
				}
				
				return super.visitCallExpression(expression, data)
			}
		}
		
		
		declaration.declarations.forEach {
			it.accept(visitor, Unit)
		}
	}
	
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
