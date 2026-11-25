# Cloudflare Pages

Proyecto: academy. Repositorio: adumun/academy. Rama de producción: master.

- Framework: Astro
- Build command: npm run build
- Build output directory: dist
- Root directory: raíz del repositorio
- Dominio: academy.adumun.cl

Antes de publicar: resolver dependencias y generar package-lock.json, verificar versión de Node compatible con las dependencias, ejecutar npm ci y npm run build, comprobar navegación, búsqueda, tema, enlaces y móvil en preview. No agregar dominio personalizado hasta validar el preview.

Referencia: https://developers.cloudflare.com/pages/framework-guides/deploy-an-astro-site/

## Primer preview

El proyecto Pages ya está conectado a GitHub. El primer build de master (aa3cbb9) falló de manera esperada porque esa rama solo contenía README. La rama feature/ADU-93-handbook-foundation contiene la aplicación; su siguiente commit activa el preview con el control de ramas predeterminado de Pages. Node se fija mediante .node-version (22.23.4). Versiones directas fijadas: Astro 7.4.13, Starlight 0.42.4 y sharp 0.35.5. La compatibilidad declarada de Starlight con Astro ^7.2.10 se verificó en su manifiesto público; la instalación real y lockfile aún requieren prueba. No usar Retry sobre el commit inicial de master.
