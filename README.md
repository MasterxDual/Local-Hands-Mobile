# ✨ Manos Locales - Local Hands App ✨

<div align="center">
  <img src="app/src/main/res/drawable/localhandslogo.png" alt="Logo" width="200" style="border-radius: 15px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);"/>

  <p align="center">
    <em>Conectando comunidades con productores locales</em> 🌱🛍️
  </p>

[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)](https://developer.android.com/jetpack/compose)
</div>

---

## 🚀 Descripción del Proyecto

**Manos Locales** es una aplicación móvil Android desarrollada para la materia de Tecnologías Móviles de la carrera de Ingeniería en Informática. La aplicación está creada íntegramente utilizando **Jetpack Compose** y tiene como objetivo principal conectar a los usuarios con productores y emprendedores locales, fomentando el consumo regional, el contacto directo y la promoción de productos auténticos.

---

## 📌 Características Principales

- **Registro e inicio de sesión**: Los usuarios pueden registrarse, iniciar sesión y cambiar su contraseña.
- **Exploración de emprendimientos**: Ver un listado de productos.
- **Filtrado avanzado**: Buscar por categoría.
- **Detalles completos**: Visualizar información como nombre, ubicación, productos ofrecidos, fotos y formas de contacto.
- **Favoritos**: Marcar productores o productos como favoritos y recibir notificaciones sobre novedades.
- **Compartir**: Compartir información de productos por WhatsApp o redes sociales.
- **Configuraciones**: Ajustar preferencias de búsqueda y notificaciones.
- **Soporte**: Enviar consultas por correo electrónico a los desarrolladores.

## 🎯 Objetivo del Proyecto

Desarrollar una aplicación Android funcional que permita:
1. Explorar una red de productos de emprendedores locales.
2. Conectarse a un servidor externo para descargar y actualizar información.
3. Interactuar de manera organizada y fluida con productos y los perfiles de sus productores.

## 🔧 Requisitos Técnicos

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

___

## 🛠️ Tecnologías Utilizadas

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

---

## 📱 Demo de la Aplicación

<div align="center">
  <img src="localhandsappvideo.gif" width="300" alt="Demo GIF"/>
</div>

---

## 📂 Estructura del Proyecto

```bash
app/
└── src/
    └── main/
        └── java/
            └── com/
                └── undef/
                    └── localhandsbrambillafunes/
                        ├── data/                         # Gestión de datos y modelos
                        ├── ui/                           # Componentes y pantallas
                        │   ├── common/                   # Elementos reutilizables (botones, diálogos, etc.)
                        │   ├── navigation/               # Configuración de la navegación
                        │   ├── screens/                  # Pantallas individuales (Login, Registro, etc.)
                        │   └── theme/                    # Configuración de temas y estilos
                        └── MainActivity.kt               # Punto de entrada de la aplicación
```

---

## 📥 Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/MasterxDual/Local-Hands-Mobile.git
   ```  
2. Abre el proyecto en **Android Studio**
3. Ejecuta en emulador o dispositivo físico

📦 **APK disponible**: [Descargar versión debug](app/build/outputs/apk/debug/app-debug.apk)

---

## 📅 Roadmap

| Versión | Estado       | Novedades                                                                 |
|---------|--------------|---------------------------------------------------------------------------|
| v1.0    | ✅ Completado | Navegación básica y pantallas estáticas                                   |
| v2.0    | 🚧 En progreso| Conexión a API real + persistencia local                                  |
| v3.0    | ⏳ Planeado   | Sistema completo de favoritos con notificaciones push                     |

---

## ✉️ Contacto

📧 **Equipo de desarrollo**:
- Tobias Funes: [tobiasfunes@hotmail.com](mailto:tobiasfunes@hotmail.com.ar)
- Agustín Brambilla: [agustinbram@gmail.com](mailto:agustinbram@gmail.com)
