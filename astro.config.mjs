import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

export default defineConfig({
  site: 'https://academy.adumun.cl',
  output: 'static',
  trailingSlash: 'always',
  integrations: [
    starlight({
      title: 'ADÜMÜN Academy',
      pagination: false,
      defaultLocale: 'root',
      locales: { root: { label: 'Español', lang: 'es' } },
      sidebar: [
        { label: 'Inicio', link: '/' },
        { label: 'Handbook', link: '/handbook/' },
        { label: 'Fundamentos', items: [{ autogenerate: { directory: 'handbook/foundations' } }] },
        { label: 'Java', items: [{ autogenerate: { directory: 'handbook/java' } }] },
        { label: 'SQL', items: [{ autogenerate: { directory: 'handbook/sql' } }] },
        { label: 'Arquitectura', items: [{ autogenerate: { directory: 'handbook/architecture' } }] },
        { label: 'Herramientas', items: [{ autogenerate: { directory: 'handbook/tools' } }] },
        { label: 'Diagnóstico', link: '/handbook/troubleshooting/' },
        { label: 'Rutas de aprendizaje', items: [{ autogenerate: { directory: 'paths' } }] },
      ],
      customCss: ['./src/styles/academy.css'],
    }),
  ],
});
