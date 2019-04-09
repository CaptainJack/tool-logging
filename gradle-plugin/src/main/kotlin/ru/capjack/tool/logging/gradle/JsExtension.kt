package ru.capjack.tool.logging.gradle

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
import org.jetbrains.kotlin.resolve.calls.callUtil.getResolvedCall
import org.jetbrains.kotlin.resolve.calls.callUtil.getType
import org.jetbrains.kotlin.resolve.calls.model.isReallySuccess
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.descriptorUtil.isExtension
import org.jetbrains.kotlin.types.KotlinType

class JsExtension : JsSyntheticTranslateExtension {
	
	private var fqNameKClass = FqName("kotlin.reflect.KClass")
	private var fqNameLogger = FqName("ru.capjack.tool.logging.Logger")
	private var fqNameOwnLogger = FqName("ru.capjack.tool.logging.ownLogger")
	private var fqNameGetLogger = FqName("ru.capjack.tool.logging.Logging.getLogger")
	private var fqNameGetLoggerExt = FqName("ru.capjack.tool.logging.getLogger")
	
	private val processedTypes = mutableSetOf<KotlinType>()
	
	override fun generateClassSyntheticParts(declaration: KtPureClassOrObject, descriptor: ClassDescriptor, translator: DeclarationBodyVisitor, context: TranslationContext) {
		
		val loggerType = context.currentModule.resolveClassByFqName(fqNameLogger, NoLookupLocation.FROM_BACKEND)?.defaultType
			?: return
		
		val visitor = object : KtTreeVisitor<Unit>() {
			
			val bindingContext = context.bindingContext()
			
			
			override fun visitReferenceExpression(expression: KtReferenceExpression, data: Unit?): Void? {
				if (expression.getType(bindingContext) == loggerType) {
					
					when (BindingUtils.getDescriptorForReferenceExpression(bindingContext, expression)?.fqNameSafe) {
						fqNameOwnLogger -> {
							val resolvedCall = expression.getResolvedCall(bindingContext)
							if (resolvedCall != null && resolvedCall.isReallySuccess()) {
								val receiver = if (resolvedCall.resultingDescriptor.isExtension) resolvedCall.extensionReceiver else resolvedCall.dispatchReceiver
								processType(context, receiver!!.type)
							}
						}
					}
				}
				
				return super.visitReferenceExpression(expression, data)
			}
			
			
			override fun visitCallExpression(expression: KtCallExpression, data: Unit): Void? {
				
				(expression.calleeExpression as? KtReferenceExpression)?.let { referenceExpression ->
					
					if (expression.getType(bindingContext) == loggerType) {
						
						when (BindingUtils.getDescriptorForReferenceExpression(bindingContext, referenceExpression)?.fqNameSafe) {
							fqNameGetLogger    -> {
								if (expression.valueArguments.size == 1 && expression.typeArguments.isEmpty()) {
									val type = BindingUtils.getTypeForExpression(bindingContext, expression.valueArguments[0].getArgumentExpression()!!)
									
									if ((type.constructor.declarationDescriptor as? ClassDescriptor)?.fqNameSafe == fqNameKClass) {
										processType(context, type.arguments[0].type)
									}
								}
							}
							fqNameGetLoggerExt -> {
								if (expression.valueArguments.isEmpty() && expression.typeArguments.size == 1) {
									processType(context, BindingUtils.getTypeByReference(bindingContext, expression.typeArguments[0].typeReference!!))
								}
							}
						}
					}
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
