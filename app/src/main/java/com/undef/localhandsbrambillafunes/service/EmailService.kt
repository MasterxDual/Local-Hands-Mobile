package com.undef.localhandsbrambillafunes.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.inject.Inject
import javax.inject.Singleton
import com.undef.localhandsbrambillafunes.BuildConfig


/**
 * Servicio para el envío de emails con dos enfoques:
 * 1. Envío directo vía SMTP (requiere configuración de servidor)
 * 2. Envío mediante intent del sistema (abre cliente de email del usuario)
 *
 * Patrón Singleton para reutilizar la misma instancia en toda la app
 */
@Singleton
class EmailService @Inject constructor() {

    private val emailUser = BuildConfig.EMAIL_USER
    private val emailPass = BuildConfig.EMAIL_PASS

    /**
     * Envía un email de verificación usando protocolo SMTP.
     *
     * @param recipientEmail Email del destinatario
     * @param verificationCode Código de verificación a enviar
     * @param appName Nombre de la aplicación para personalización
     * @return Result<Boolean> con éxito/fracaso de la operación
     *
     * Requisitos:
     * 1. Dependencias en build.gradle:
     *    implementation 'com.sun.mail:android-mail:1.6.6'
     *    implementation 'com.sun.mail:android-activation:1.6.7'
     * 2. Permisos en AndroidManifest.xml:
     *    <uses-permission android:name="android.permission.INTERNET"/>
     *
     * Notas de seguridad:
     * - Usar contraseña de aplicación (no la personal)
     * - Considerar implementar OAuth2 para Gmail
     */
    suspend fun sendVerificationEmail(
        recipientEmail: String,
        verificationCode: String,
        appName: String = "LocalHands"
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) { // Ejecuta en hilo IO para no bloquear UI
            try {
                // Configuración SMTP para Gmail (modificar para otros proveedores)
                val props = Properties().apply {
                    put("mail.smtp.host", "smtp.gmail.com") // Servidor SMTP
                    put("mail.smtp.port", "587") // Puerto seguro
                    put("mail.smtp.auth", "true") // Requiere autenticación
                    put("mail.smtp.starttls.enable", "true")  // Encriptación TLS
                }

                // Autenticación con credenciales (TODO: Almacenar seguramente en producción)
                val session = Session.getInstance(props, object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(
                            emailUser,   // Reemplazar con tu email
                            emailPass     // Contraseña de aplicacion de Google
                        )
                    }
                })

                // Construcción del mensaje
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(emailUser)) // Email remitente
                    setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(recipientEmail) // Destinatario
                    )
                    subject = "Codigo de Verificación - $appName" // Asunto
                    setText(createVerificationEmailBody(verificationCode, appName)) // Cuerpo
                }

                Transport.send(message) // Envio real
                Result.success(true)
            } catch (e: MessagingException) {
                Result.failure(Exception("Error de configuracion SMTP: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Error inesperado: ${e.message}"))
            }
        }
    }

    /*

    /**
     * Alternativa que abre el cliente de email del dispositivo.
     *
     * @param context Contexto de Android
     * @param recipientEmail Email del destinatario
     * @param verificationCode Código a enviar
     * @param appName Nombre de la app
     * @return Boolean indicando si se lanzó el intent exitosamente
     *
     * Ventajas:
     * - No requiere configuración SMTP
     * - Respeta el cliente de email preferido del usuario
     *
     * Desventajas:
     * - Requiere acción manual del usuario
     * - No es automático
     */
    fun sendEmailViaIntent(
        context: Context,
        recipientEmail: String,
        verificationCode: String,
        appName: String = "LocalHands"
    ): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                putExtra(Intent.EXTRA_SUBJECT, "Código de Verificación - $appName")
                putExtra(Intent.EXTRA_TEXT, createVerificationEmailBody(verificationCode, appName)) // Cuerpo
            }

            // Verifica que exista una app que maneje el intent
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                true
            } else {
                false // No hay apps de email instaladas
            }
        } catch (e: Exception) {
            false // Error al lanzar el intent
        }
    }

     */


    /**
     * Genera el cuerpo del email de verificación con formato consistente.
     *
     * @param code Código de verificación
     * @param appName Nombre de la aplicación
     * @return String con el cuerpo del email formateado
     *
     * Mejoras posibles:
     * - Usar plantillas HTML para diseño enriquecido
     * - Añadir enlaces de acción directa
     */
    private fun createVerificationEmailBody(code: String, appName: String): String {
        return """
            ¡Hola!
            
            Tu código de verificación para $appName es:
            
            🔐 $code 🔐
            
            Este código expirará en 10 minutos.
            
            Si no solicitaste este código, por favor ignora este mensaje.
            
            Atentamente,
            El equipo de $appName
            
            ---
            Este es un mensaje automático, por favor no respondas.
        """.trimIndent() // Elimina indentación común
    }
}

/*
Configuración adicional requerida:

1. AndroidManifest.xml:
-----------------------------------------
<manifest ...>
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <!-- Para el intent de email -->
    <queries>
        <intent>
            <action android:name="android.intent.action.SENDTO" />
            <data android:scheme="mailto" />
        </intent>
    </queries>
</manifest>

2. build.gradle (app):
-----------------------------------------
dependencies {
    // Para envío SMTP
    implementation 'com.sun.mail:android-mail:1.6.6'
    implementation 'com.sun.mail:android-activation:1.6.7'

    // Para inyección de dependencias
    implementation 'javax.inject:javax.inject:1'
}
*/