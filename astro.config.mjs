import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';

export default defineConfig({
  site: 'https://academy.adumun.cl',
  output: 'static',
  trailingSlash: 'always',
  integrations: [
    starlight({
      title: 'ADÜMÜN Academy',
      defaultLocale: 'root',
      locales: { root: { label: 'Español', lang: 'es' } },
      sidebar: [
        { label: 'Inicio', link: '/' },
        { label: 'Handbook', link: '/handbook/' },
        { label: 'Java', autogenerate: { directory: 'handbook/java' } },
        { label: 'SQL', autogenerate: { directory: 'handbook/sql' } },
        { label: 'Arquitectura', autogenerate: { directory: 'handbook/architecture' } },
        { label: 'Rutas de aprendizaje', autogenerate: { directory: 'paths' } },
      ],
      customCss: ['./src/styles/academy.css'],
    }),
  ],
});
