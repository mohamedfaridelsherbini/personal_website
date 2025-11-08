# Roadmap: Next Enhancements

| Theme | Opportunity | Notes |
|-------|-------------|-------|
| **Content pipeline** | Support Markdown + front-matter or a headless CMS feed alongside JSON | Swap the `ContentLoader` implementation without touching the presentation layer |
| **Pre-rendering** | Generate static HTML during build using the existing render cache | Speeds up cold starts and unlocks CDN hosting |
| **Interactive storytelling** | Wire skill clusters to experience cards via data attributes for progressive enhancement | Keeps SSR clean while enabling future JS animations |
| **Accessibility & motion** | Add reduced-motion and high-contrast toggles, audit neon palette contrast | Retains the aesthetic while respecting user preferences |
| **Observability** | Layer in Plausible/Fathom analytics and schedule the uptime checker via cron or GitHub Actions | Builds on `bin/uptime-check.sh` |
| **Contact capture** | Add a lightweight intake form (Formspree, Netlify Forms, etc.) with spam protection | Complements the “Partner with me” CTA |
