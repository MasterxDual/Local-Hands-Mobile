# Manos Locales

![Manos Locales Logo](app/src/main/res/drawable/localhandslogo.png)

## Descripción del Proyecto

**Manos Locales** es una aplicación móvil Android desarrollada para la materia de Tecnologías Móviles de la carrera de Ingeniería en Informática. La aplicación está creada íntegramente utilizando **Jetpack Compose** y tiene como objetivo principal conectar a los usuarios con productores y emprendedores locales, fomentando el consumo regional, el contacto directo y la promoción de productos auténticos.

## Características Principales

- **Registro e inicio de sesión**: Los usuarios pueden registrarse, iniciar sesión y cambiar su contraseña.
- **Exploración de emprendimientos**: Ver un listado de productos.
- **Filtrado avanzado**: Buscar por categoría.
- **Detalles completos**: Visualizar información como nombre, ubicación, productos ofrecidos, fotos y formas de contacto.
- **Favoritos**: Marcar productores o productos como favoritos y recibir notificaciones sobre novedades.
- **Compartir**: Compartir información de productos por WhatsApp o redes sociales.
- **Configuraciones**: Ajustar preferencias de búsqueda y notificaciones.
- **Soporte**: Enviar consultas por correo electrónico a los desarrolladores.

## Objetivo del Proyecto

Desarrollar una aplicación Android funcional que permita:
1. Explorar una red de productos de emprendedores locales.
2. Conectarse a un servidor externo para descargar y actualizar información.
3. Interactuar de manera organizada y fluida con productos y los perfiles de sus productores.

## Requisitos Técnicos

### Requerimientos Funcionales

1. **Pantallas**:
    - Pantalla de bienvenida (Splash) para mostrar el logo y cargar datos iniciales.
    - Pantallas para login, registro y cambio de contraseña.
    - Pantalla principal con un listado de productos.
    - Pantalla para detalles de productos o emprendedores.
    - Pantalla de configuraciones (Settings).

2. **Funcionalidades**:
    - Navegación fluida entre pantallas (Navigation Compose).
    - Buscador por categoría, ciudad o vendedores.
    - Favoritos con notificaciones para actualizaciones.
    - Conexión a una API para descargar y actualizar datos.
    - Manejo de permisos para ubicación y otras funcionalidades del dispositivo.
    - Envío de correo al desarrollador usando Intents.

### Requerimientos No Funcionales

- **Usabilidad**: Interfaz intuitiva y fácil de usar.
- **Internacionalización**: Soporte para múltiples idiomas.
- **Sincronización con GitHub**: Proyecto subido y actualizado en un repositorio público.
- **Hilos y servicios**: Operaciones de red en segundo plano con hilos o corutinas.
- **Arquitectura**: Uso de Activities, Fragments y Compose según necesidad.

## Estado del Proyecto

Actualmente, la aplicación está en su **primera entrega**, que incluye:
- Navegación entre pantallas básicas: Login, Registro, Pantalla Principal y Settings.
- Información estática para estas pantallas.
- Prototipo funcional disponible como APK.

## Cómo Ejecutar el Proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/MasterxDual/Local-Hands-Mobile.git
   ```
2. Abrir el proyecto en **Android Studio**.
3. Sincronizar las dependencias del proyecto.
4. Ejecutar la aplicación en un emulador o dispositivo físico.

## Compilación APK

El archivo APK compilado se encuentra disponible en el repositorio: [Descargar APK](app/build/outputs/apk/debug/app-debug.apk) <!-- Reemplazar con el enlace real -->

## Demo

A continuación, un GIF que muestra el funcionamiento actual de la aplicación:

![Demo de la Aplicación](path/to/demo.gif) <!-- Reemplazar con el enlace real -->

## Estructura del Proyecto

La estructura del proyecto sigue la convención estándar de Android con módulos organizados de la siguiente manera:
```
app/
└── src/
    └── main/
        └── java/
            └── com/
                └── undef/
                    └── localhandsbrambillafunes/
                        ├── data/            # Gestión de datos y modelos
                        ├── ui/              # Componentes y pantallas
                        │   ├── common/      # Elementos reutilizables (botones, diálogos, etc.)
                        │   ├── navigation/  # Configuración de la navegación
                        │   ├── screens/     # Pantallas individuales (Login, Registro, etc.)
                        │   └── theme/       # Configuración de temas y estilos
                        └── MainActivity.kt  # Punto de entrada de la aplicación
```

## Tecnologías Utilizadas

- **Lenguaje de Programación**: Kotlin
- **Framework UI**: Jetpack Compose
- **Herramientas de Desarrollo**:
    - Android Studio
    - GitHub para control de versiones
- **Librerías**:
    - Navigation Compose
    - Retrofit para llamadas a la API (futuro)
    - Coroutines para manejo de hilos (futuro)
    - Room para base de datos local (futuro)

## Próximos Pasos

En futuras iteraciones, se agregarán:
- Conexión a la API para datos dinámicos.
- Funcionalidades completas de favoritos y notificaciones.
- Mejoras en la interfaz gráfica y experiencia de usuario.

## Contribuciones

Contribuciones son bienvenidas. Por favor, abre un issue o pull request para cualquier mejora o corrección.

## Contacto

Para consultas o soporte, envía un correo a: 
- [tobiasfunes@hotmail.com](mailto:tobiasfunes@hotmail.com.ar)
- [agustinbram@gmail.com](mailto:agustinbram@gmail.com)

---

**Manos Locales** - Proyecto desarrollado para la materia de Tecnologías Móviles.