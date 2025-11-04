package com.personalwebsite.presentation.views

import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.entities.ProjectLink
import com.personalwebsite.domain.entities.WorkExperience
import com.personalwebsite.presentation.models.WebsiteViewModel
// Using buildString and appendLine for HTML generation

/**
 * HTML implementation of WebsiteView
 * This is where all the HTML magic happens
 */
class HtmlWebsiteView : WebsiteView {
    
    override fun render(viewModel: WebsiteViewModel): String {
        return buildString {
            val personalInfo = viewModel.personalInfo
            val siteTitle = "${personalInfo.name} - ${personalInfo.title}"
            val baseUrl = "https://www.mohamedfaridelsherbini.com"
            val metaDescription = "Senior Android engineer crafting Kotlin and Jetpack Compose experiences for fintech and product teams across Europe. Currently at Check24 in Munich, open to remote consulting and leadership opportunities."
            val encodedDescription = metaDescription.replace("\"", "&quot;")
            val jsonSafeDescription = metaDescription.replace("\"", "\\\"")
            val socialImageUrl = "$baseUrl/static/images/profile-social.jpg"
            val personSchema = """
                {
                  "@context": "https://schema.org",
                  "@type": "Person",
                  "name": "${personalInfo.name}",
                  "jobTitle": "${personalInfo.title}",
                  "description": "$jsonSafeDescription",
                  "email": "mailto:${personalInfo.email}",
                  "telephone": "${personalInfo.phone}",
                  "url": "$baseUrl",
                  "image": "$socialImageUrl",
                  "sameAs": [
                    "${personalInfo.linkedin}",
                    "${personalInfo.github}"
                  ],
                  "author": {
                    "@type": "Person",
                    "name": "${personalInfo.name}"
                  },
                  "datePublished": "2025-11-02",
                  "address": {
                    "@type": "PostalAddress",
                    "addressLocality": "${personalInfo.location}"
                  }
                }
            """.trimIndent()

            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
            appendLine("<head>")
            appendLine("    <meta charset=\"utf-8\"/>")
            appendLine("    <link crossorigin=\"\" href=\"https://fonts.gstatic.com/\" rel=\"preconnect\"/>")
            appendLine("    <link as=\"style\" href=\"https://fonts.googleapis.com/css2?display=swap&amp;family=Noto+Sans%3Awght%40400%3B500%3B700%3B900&amp;family=Spline+Sans%3Awght%40400%3B500%3B700\" onload=\"this.rel='stylesheet'\" rel=\"stylesheet\"/>")
            appendLine("    <title>$siteTitle</title>")
            appendLine("    <link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"/static/images/favicon-portrait.png?v=2\">")
            appendLine("    <link rel=\"icon\" type=\"image/png\" sizes=\"64x64\" href=\"/static/images/favicon-portrait.png?v=2\">")
            appendLine("    <link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"/static/images/favicon-portrait.png?v=2\">")
            appendLine("    <link rel=\"icon\" type=\"image/x-icon\" href=\"/static/images/favicon.ico?v=2\">")
            appendLine("    <link rel=\"stylesheet\" href=\"/static/css/style.css\" type=\"text/css\">")
            appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
            appendLine("    <meta name=\"description\" content=\"$encodedDescription\">")
            appendLine("    <meta name=\"author\" content=\"${personalInfo.name}\">")
            appendLine("    <meta property=\"article:published_time\" content=\"2025-11-02T00:00:00+01:00\">")
            appendLine("    <link rel=\"canonical\" href=\"$baseUrl/\">")
            appendLine("    <meta property=\"og:type\" content=\"website\">")
            appendLine("    <meta property=\"og:title\" content=\"$siteTitle\">")
            appendLine("    <meta property=\"og:description\" content=\"$encodedDescription\">")
            appendLine("    <meta property=\"og:url\" content=\"$baseUrl/\">")
            appendLine("    <meta name=\"image\" property=\"og:image\" content=\"$socialImageUrl\">")
            appendLine("    <meta property=\"og:image\" content=\"$socialImageUrl\">")
            appendLine("    <meta property=\"og:image:width\" content=\"800\">")
            appendLine("    <meta property=\"og:image:height\" content=\"800\">")
            appendLine("    <meta property=\"og:image:type\" content=\"image/jpeg\">")
            appendLine("    <meta property=\"og:image:alt\" content=\"Portrait of ${personalInfo.name}\">")
            appendLine("    <meta name=\"twitter:card\" content=\"summary_large_image\">")
            appendLine("    <meta name=\"twitter:title\" content=\"$siteTitle\">")
            appendLine("    <meta name=\"twitter:description\" content=\"$encodedDescription\">")
            appendLine("    <meta name=\"twitter:image\" content=\"$socialImageUrl\">")
            appendLine("    <meta name=\"twitter:image:alt\" content=\"Portrait of ${personalInfo.name}\">")
            appendLine("    <script src=\"/static/js/smooth-scroll.js\"></script>")
            appendLine("    <script src=\"/static/js/cursor-buddy.js\"></script>")
            appendLine("    <script type=\"application/ld+json\">")
            appendLine("    $personSchema")
            appendLine("    </script>")
            appendLine("    <script>")
            appendLine("        function copyToClipboard(text) {")
            appendLine("            navigator.clipboard.writeText(text).then(function() {")
            appendLine("                alert('Copied to clipboard: ' + text);")
            appendLine("            });")
            appendLine("        }")
            appendLine("    </script>")
            appendLine("</head>")
            appendLine("<body class=\"bg-[#111714] font-['Spline_Sans']\">")
            
            // Header - kept it simple, might add animations later
            appendLine("    <header>")
            appendLine("        <div class=\"header-left\">")
            appendLine("            <img src=\"/static/images/profile-social.jpg\" alt=\"Portrait of ${personalInfo.name}\" class=\"logo-badge\">")
            appendLine("            <h2 class=\"header-title\">${viewModel.personalInfo.name}</h2>")
            appendLine("        </div>")
            appendLine("        <button class=\"nav-toggle\" type=\"button\" aria-label=\"Toggle navigation\" aria-expanded=\"false\" data-nav-toggle>")
            appendLine("            <span class=\"nav-toggle-bar\"></span>")
            appendLine("            <span class=\"nav-toggle-bar\"></span>")
            appendLine("            <span class=\"nav-toggle-bar\"></span>")
            appendLine("        </button>")
            appendLine("        <nav class=\"nav-panel\" data-nav-panel>")
            appendLine("            <div class=\"nav-links\">")
            appendLine("                <a class=\"nav-link\" href=\"#about\">About</a>")
            appendLine("                <a class=\"nav-link\" href=\"#skills\">Skills</a>")
            appendLine("                <a class=\"nav-link\" href=\"#personal-projects\">Projects</a>")
            appendLine("                <a class=\"nav-link\" href=\"#contact\">Contact</a>")
            appendLine("            </div>")
            appendLine("            <div class=\"nav-actions\">")
            appendLine("                <a href=\"mailto:${viewModel.personalInfo.email}\" class=\"contact-button\">Let's Talk</a>")
            appendLine("                <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"resume-button\" target=\"_blank\" download=\"Mohamed_ElSherbini_Resume.pdf\">Resume</a>")
            appendLine("            </div>")
            appendLine("        </nav>")
            appendLine("    </header>")
            
            appendLine("    <main>")
            
            // Home Section - this took a few iterations to get right
        appendLine("        <section class=\"hero\" id=\"home\" data-scroll-target=\"home\">")
        appendLine("            <div class=\"hero-content\">")
        appendLine("                <div class=\"hero-text cluster-card\">")
        appendLine("                    <div class=\"hero-chip-row\">")
        appendLine("                        <span class=\"hero-chip\">Augsburg</span>")
        appendLine("                        <span class=\"hero-chip\">Kotlin</span>")
        appendLine("                        <span class=\"hero-chip\">Swift</span>")
        appendLine("                        <span class=\"hero-chip\">CI/CD</span>")
        appendLine("                        <span class=\"hero-chip\">KMP</span>")
        appendLine("                    </div>")
        appendLine("                    <h1 class=\"hero-title\">Resilient mobile apps for a neon future</h1>")
        appendLine("                    <p class=\"hero-subtitle\">Senior Android Engineer blending Kotlin, Swift, and automation to ship maintainable products that scale globally. I design architecture, pipelines, and UX together so regulated journeys stay fast, secure, and delightful.</p>")
        appendLine("                    <ul class=\"hero-highlight-list\">")
        appendLine("                        <li>Composable Android & KMP architectures rolled out across multi-brand platforms</li>")
        appendLine("                        <li>Automation frameworks (Selenium, Appium) powering Android/iOS CI/CD</li>")
        appendLine("                        <li>Reliability, performance, and security-first mobile experiences for millions of users.</li>")
        appendLine("                    </ul>")
        appendLine("                    <div class=\"hero-actions hero-cta-row\">")
        appendLine("                        <a href=\"#contact\" class=\"primary-button\">Partner with me</a>")
        appendLine("                        <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"secondary-button\" target=\"_blank\" rel=\"noopener\">Download résumé</a>")
        appendLine("                    </div>")
        appendLine("                    <div class=\"hero-meta-row\">")
        appendLine("                        <div class=\"hero-meta-pill\">")
        appendLine("                            <span class=\"hero-meta-label\">Availability</span>")
        appendLine("                            <span class=\"hero-meta-value\">Q1 2026</span>")
        appendLine("                        </div>")
        appendLine("                        <div class=\"hero-meta-pill\">")
        appendLine("                            <span class=\"hero-meta-label\">Current mission</span>")
        appendLine("                            <span class=\"hero-meta-value\">CHECK24 Mobile Platform</span>")
        appendLine("                        </div>")
        appendLine("                    </div>")
        appendLine("                </div>")
        appendLine("                <div class=\"hero-panels\">")
        appendLine("                    <div class=\"hero-panel cluster-card hero-signal\">")
        appendLine("                        <h3>Systems online</h3>")
        appendLine("                        <p>Composable design systems, real-time observability, and battle-tested release trains keep every build stable under neon lights.</p>")
        appendLine("                        <ul class=\"hero-signal-list\">")
        appendLine("                            <li>Jetpack Compose and KMP rolled out across multi-brand Android platforms</li>")
        appendLine("                            <li>Automation frameworks (Selenium, Appium) powering shared Android/iOS pipelines</li>")
        appendLine("                            <li>Security reviews covering encryption, biometric flows, and fintech compliance</li>")
        appendLine("                        </ul>")
        appendLine("                        <div class=\"hero-tag-cloud\">")
        appendLine("                            <span class=\"hero-tag\">Kotlin</span>")
        appendLine("                            <span class=\"hero-tag\">Compose</span>")
        appendLine("                            <span class=\"hero-tag\">Swift</span>")
        appendLine("                            <span class=\"hero-tag\">KMP</span>")
        appendLine("                            <span class=\"hero-tag\">CI/CD</span>")
        appendLine("                        </div>")
        appendLine("                    </div>")
        appendLine("                </div>")
        appendLine("            </div>")
        appendLine("        </section>")
            
            // What I Do Section - might add more features later
        appendLine("        <section class=\"features\">")
        appendLine("            <div class=\"section-heading\">")
        appendLine("                <p class=\"section-kicker\">Core strengths</p>")
        appendLine("                <h2 class=\"section-title\">From blueprint to launch</h2>")
        appendLine("            </div>")
        appendLine("            <p class=\"section-subtitle\">Composable architecture, end-to-end automation, and fintech reliability are the pillars behind every engagement I take on.</p>")
        appendLine("            <div class=\"feature-grid\">")
        appendLine("                <div class=\"feature cluster-card\">")
        appendLine("                    <div class=\"feature-icon\">⚙️</div>")
        appendLine("                    <h3>Composable architecture</h3>")
        appendLine("                    <p>Radically modular Kotlin base layers, sealed contracts, and Compose-first UI kits so every squad ships independently without breaking the core system.</p>")
        appendLine("                </div>")
        appendLine("                <div class=\"feature cluster-card\">")
        appendLine("                    <div class=\"feature-icon\">📡</div>")
        appendLine("                    <h3>Observability & automation</h3>")
        appendLine("                    <p>Cross-platform UI automation frameworks (Selenium, Appium) wired into Jenkins, Docker, and analytics stacks so release trains stay green and insights stay live.</p>")
        appendLine("                </div>")
        appendLine("                <div class=\"feature cluster-card\">")
        appendLine("                    <div class=\"feature-icon\">🔒</div>")
        appendLine("                    <h3>Reliability at scale</h3>")
        appendLine("                    <p>Reliability, performance, and security-first mobile experiences for millions of users, built into every architecture decision, test, and deployment.</p>")
        appendLine("                </div>")
        appendLine("            </div>")
            appendLine("        </section>")
            
            // About Section - this section took the most time to get right
            appendLine("        <section class=\"page-section\" id=\"about\" data-scroll-target=\"about\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                <div class=\"about-header\">")
            appendLine("                    <h1 class=\"about-name\">${viewModel.personalInfo.name}</h1>")
            appendLine("                    <p class=\"about-title\">${viewModel.personalInfo.title}</p>")
            appendLine("                    <p class=\"about-location\">📍 ${viewModel.personalInfo.location}</p>")
            appendLine("                </div>")
            appendLine("                <div class=\"section-heading\">")
            appendLine("                    <p class=\"section-kicker\">Human layer</p>")
            appendLine("                    <h2 class=\"section-title\">About Me</h2>")
            appendLine("                </div>")
            appendLine("                <p class=\"about-description\">Senior Android Engineer with 8+ years of experience building resilient, high-quality mobile applications using Kotlin and Java, plus growing iOS expertise with Swift. Specialized in Android architecture, Kotlin Multiplatform (KMP), CI/CD automation, and multi-module scalability that keeps releases predictable.</p>")
            appendLine("                <p class=\"about-description\">Proficient in Appium, Selenium, Docker, Jenkins, and Spring Boot microservices. I focus on maintainable products that blend technical excellence with delightful user journeys, especially across finance, automation, and highly regulated experiences.</p>")
        appendLine("                <ul class=\"about-points\">")
        appendLine("                    <li>Composable architecture — modular Kotlin bases, KMP adoption, and Jetpack Compose design systems that scale across brands.</li>")
        appendLine("                    <li>Observability & automation — Selenium/Appium frameworks, Jenkins pipelines, and live dashboards keeping KPIs in sight.</li>")
        appendLine("                    <li>Reliability, performance, and security-first mobile experiences for millions of users.</li>")
        appendLine("                </ul>")
            
            // Work Experience
            appendLine("                <div class=\"section-heading\">")
            appendLine("                    <p class=\"section-kicker\">Career signal</p>")
            appendLine("                    <h2 class=\"section-title\">Experience</h2>")
            appendLine("                </div>")
            appendLine("                <div class=\"experience-grid\">")
            append(renderExperienceCards(viewModel.workExperience))
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // Skills Section
            appendLine("        <section class=\"page-section\" id=\"skills\" data-scroll-target=\"skills\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                <div class=\"section-heading\">")
            appendLine("                    <p class=\"section-kicker\">Capability stack</p>")
            appendLine("                    <h1 class=\"section-title\">Technical Skills</h1>")
            appendLine("                </div>")
            appendLine("                <p class=\"section-subtitle\">Tooling and languages I reach for when blueprinting performant, future-proof Android ecosystems.</p>")
            appendLine("                <div class=\"skills-grid\">")
            viewModel.skills.forEach { skill ->
                appendLine("                    <div class=\"skill-cluster cluster-card\">")
                appendLine("                        <div class=\"skill-cluster-header\">")
                appendLine("                            <span class=\"skill-cluster-badge\">${skill.category}</span>")
                appendLine("                        </div>")
                appendLine("                        <div class=\"skill-cluster-tags\">")
                skill.items.forEach { item ->
                    appendLine("                            <div class=\"skill-tag\">$item</div>")
                }
                appendLine("                        </div>")
                appendLine("                    </div>")
            }
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            
            // Personal Projects Section
            appendLine("        <section class=\"page-section\" id=\"personal-projects\" data-scroll-target=\"personal-projects\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                <div class=\"section-heading\">")
            appendLine("                    <p class=\"section-kicker\">Lab notes</p>")
            appendLine("                    <h1 class=\"section-title\">Notable Projects & Open Source</h1>")
            appendLine("                </div>")
            appendLine("                <p class=\"section-subtitle\">A blend of flagship launches, automation frameworks, and open source experiments that showcase architecture, AI, and platform integration work.</p>")
            appendLine("                <div class=\"projects-grid\">")
            viewModel.personalProjects.forEach { project ->
                appendLine("                    <a href=\"/projects/${project.slug}\" class=\"project-card personal-project-card cluster-card\">")
                appendLine("                        <div class=\"project-content\">")
                appendLine("                            <div class=\"project-header\">")
                appendLine("                                <h3 class=\"project-title\">${project.name}</h3>")
                appendLine("                                <span class=\"project-category\">${project.category}</span>")
                appendLine("                            </div>")
                appendLine("                            <p class=\"project-description\">${project.description}</p>")
                appendLine("                            <div class=\"project-tech-stack\">")
                project.techStack.split(" • ").forEach { tech ->
                    appendLine("                                <span class=\"tech-tag\">$tech</span>")
                }
                appendLine("                            </div>")
                appendLine("                        </div>")
                appendLine("                    </a>")
            }
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // Contact Section
            appendLine("        <section class=\"page-section\" id=\"contact\" data-scroll-target=\"contact\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                <div class=\"section-heading\">")
            appendLine("                    <p class=\"section-kicker\">Comm link</p>")
            appendLine("                    <h1 class=\"section-title\">Get In Touch</h1>")
            appendLine("                </div>")
            appendLine("                <p class=\"section-subtitle\">Tell me about the Android platform you’re scaling, the audit you need, or the release train that keeps wobbling. I’ll get back within 24 hours.</p>")
            appendLine("                <div class=\"contact-info\">")
            appendLine("                    <a href=\"mailto:${viewModel.personalInfo.email}\" class=\"contact-item\">")
            appendLine("                        <span class=\"contact-label\">Email</span>")
            appendLine("                        <h3>${viewModel.personalInfo.email}</h3>")
            appendLine("                        <p>Fastest path for briefs, contracts, and support requests.</p>")
            appendLine("                    </a>")
            appendLine("                    <a href=\"${viewModel.personalInfo.linkedin}\" target=\"_blank\" rel=\"noopener\" class=\"contact-item\">")
            appendLine("                        <span class=\"contact-label\">LinkedIn</span>")
            appendLine("                        <h3>linkedin.com/in/mohamedfaridelsherbini</h3>")
            appendLine("                        <p>Follow product drops, speaking gigs, and fintech notes.</p>")
            appendLine("                    </a>")
            appendLine("                    <a href=\"${viewModel.personalInfo.github}\" target=\"_blank\" rel=\"noopener\" class=\"contact-item\">")
            appendLine("                        <span class=\"contact-label\">GitHub</span>")
            appendLine("                        <h3>github.com/mohamedfaridelsherbini</h3>")
            appendLine("                        <p>Inspect OSS libraries, experiments, and CI templates.</p>")
            appendLine("                    </a>")
            appendLine("                </div>")
            
            // Languages
            appendLine("                <div class=\"languages-section\">")
            appendLine("                    <div class=\"section-heading\">")
            appendLine("                        <p class=\"section-kicker\">Signal clarity</p>")
            appendLine("                        <h2 class=\"section-title\">Languages</h2>")
            appendLine("                    </div>")
            appendLine("                    <div class=\"languages-grid\">")
            viewModel.languages.forEach { language ->
                appendLine("                        <div class=\"language-item\">")
                appendLine("                            <span class=\"language\">${language.name}</span>")
                appendLine("                            <span class=\"level\">${language.level}</span>")
                appendLine("                        </div>")
            }
            appendLine("                    </div>")
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            appendLine("        <section class=\"quote-section\">")
            appendLine("            <p class=\"quote-text\">\"Code for clarity, automate for consistency, and build for longevity.\"</p>")
            appendLine("            <span class=\"quote-author\">— ${viewModel.personalInfo.name}</span>")
            appendLine("        </section>")
            appendLine("    </main>")
            appendLine("    <footer>")
            appendLine("        <p>© 2025 ${viewModel.personalInfo.name}. Built with Kotlin, Ktor & Clean Architecture.</p>")
            appendLine("    </footer>")
            appendLine("</body>")
            appendLine("</html>")
    }
}

    override fun renderProject(project: PersonalProject, viewModel: WebsiteViewModel): String {
        val personalInfo = viewModel.personalInfo
        val baseUrl = "https://www.mohamedfaridelsherbini.com"
        val siteTitle = "${project.name} – Case Study | ${personalInfo.name}"
        val metaDescription = project.summary
        val encodedDescription = metaDescription.replace("\"", "&quot;")
        val socialImageUrl = "$baseUrl/static/images/profile-social.jpg"
        val otherProjects = viewModel.personalProjects.filter { it.slug != project.slug }

        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
            appendLine("<head>")
            appendLine("    <meta charset=\"utf-8\"/>")
            appendLine("    <link crossorigin=\"\" href=\"https://fonts.gstatic.com/\" rel=\"preconnect\"/>")
            appendLine("    <link as=\"style\" href=\"https://fonts.googleapis.com/css2?display=swap&amp;family=Noto+Sans%3Awght%40400%3B500%3B700%3B900&amp;family=Spline+Sans%3Awght%40400%3B500%3B700\" onload=\"this.rel='stylesheet'\" rel=\"stylesheet\"/>")
            appendLine("    <title>$siteTitle</title>")
            appendLine("    <link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"/static/images/favicon-portrait.png?v=2\">")
            appendLine("    <link rel=\"icon\" type=\"image/png\" sizes=\"64x64\" href=\"/static/images/favicon-portrait.png?v=2\">")
            appendLine("    <link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"/static/images/favicon-portrait.png?v=2\">")
            appendLine("    <link rel=\"icon\" type=\"image/x-icon\" href=\"/static/images/favicon.ico?v=2\">")
            appendLine("    <link rel=\"stylesheet\" href=\"/static/css/style.css\" type=\"text/css\">")
            appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
            appendLine("    <meta name=\"description\" content=\"$encodedDescription\">")
            appendLine("    <meta property=\"og:type\" content=\"article\">")
            appendLine("    <meta property=\"og:title\" content=\"$siteTitle\">")
            appendLine("    <meta property=\"og:description\" content=\"$encodedDescription\">")
            appendLine("    <meta property=\"og:url\" content=\"$baseUrl/projects/${project.slug}\">")
            appendLine("    <meta property=\"og:image\" content=\"$socialImageUrl\">")
            appendLine("    <meta property=\"og:image:alt\" content=\"${project.name} case study\">")
            appendLine("    <meta name=\"twitter:card\" content=\"summary_large_image\">")
            appendLine("    <meta name=\"twitter:title\" content=\"$siteTitle\">")
            appendLine("    <meta name=\"twitter:description\" content=\"$encodedDescription\">")
            appendLine("    <meta name=\"twitter:image\" content=\"$socialImageUrl\">")
            appendLine("</head>")
            appendLine("<body class=\"bg-[#111714] font-['Spline_Sans']\">")
            appendLine("    <header>")
            appendLine("        <div class=\"header-left\">")
            appendLine("            <img src=\"/static/images/profile-social.jpg\" alt=\"Portrait of ${personalInfo.name}\" class=\"logo-badge\">")
            appendLine("            <h2 class=\"header-title\">${personalInfo.name}</h2>")
            appendLine("        </div>")
            appendLine("        <nav class=\"nav-panel is-static\">")
            appendLine("            <div class=\"nav-links\">")
            appendLine("                <a class=\"nav-link\" href=\"/\">Home</a>")
            appendLine("                <a class=\"nav-link\" href=\"/#about\">About</a>")
            appendLine("                <a class=\"nav-link\" href=\"/#skills\">Skills</a>")
            appendLine("                <a class=\"nav-link\" href=\"/#personal-projects\">Projects</a>")
            appendLine("                <a class=\"nav-link\" href=\"/#contact\">Contact</a>")
            appendLine("            </div>")
            appendLine("            <div class=\"nav-actions\">")
            appendLine("                <a href=\"mailto:${personalInfo.email}\" class=\"contact-button\">Let's Talk</a>")
            appendLine("                <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"resume-button\" target=\"_blank\" download=\"Mohamed_ElSherbini_Resume.pdf\">Resume</a>")
            appendLine("            </div>")
            appendLine("        </nav>")
            appendLine("    </header>")

            appendLine("    <main class=\"project-detail\">")
            appendLine("        <section class=\"project-hero cluster-card\">")
            if (project.timeline != null || project.role != null) {
                appendLine("            <div class=\"project-pills\">")
                project.timeline?.let {
                    appendLine("                <span class=\"project-pill\">$it</span>")
                }
                project.role?.let {
                    appendLine("                <span class=\"project-pill\">$it</span>")
                }
                appendLine("            </div>")
            }
            appendLine("            <h1 class=\"project-title\">${project.name}</h1>")
            appendLine("            <p class=\"project-summary\">${project.summary}</p>")
            appendLine("            <div class=\"project-meta\">")
            appendLine("                <div class=\"project-meta-block\">")
            appendLine("                    <h3>Stack</h3>")
            appendLine("                    <div class=\"project-tags\">")
            project.techStack.split(" • ").forEach { tech ->
                appendLine("                        <span class=\"project-tag\">$tech</span>")
            }
            appendLine("                    </div>")
            appendLine("                </div>")
            if (project.metrics.isNotEmpty()) {
                appendLine("                <div class=\"project-meta-block\">")
                appendLine("                    <h3>Metrics</h3>")
                appendLine("                    <ul class=\"project-metrics\">")
                project.metrics.forEach { metric ->
                    appendLine("                        <li>$metric</li>")
                }
                appendLine("                    </ul>")
                appendLine("                </div>")
            }
            if (project.links.isNotEmpty()) {
                appendLine("                <div class=\"project-meta-block\">")
                appendLine("                    <h3>Links</h3>")
                appendLine("                    <div class=\"project-links\">")
                project.links.forEach { link ->
                    appendLine("                        <a class=\"project-link\" href=\"${link.url}\" target=\"_blank\" rel=\"noopener\">${link.label}</a>")
                }
                appendLine("                    </div>")
                appendLine("                </div>")
            }
            appendLine("            </div>")
            appendLine("        </section>")

            if (project.highlights.isNotEmpty()) {
                appendLine("        <section class=\"project-section cluster-card\">")
                appendLine("            <h2>Highlights</h2>")
                appendLine("            <ul class=\"project-highlights\">")
                project.highlights.forEach { highlight ->
                    appendLine("                <li>$highlight</li>")
                }
                appendLine("            </ul>")
                appendLine("        </section>")
            }

            if (otherProjects.isNotEmpty()) {
                appendLine("        <section class=\"project-section\">")
                appendLine("            <div class=\"section-heading\">")
                appendLine("                <p class=\"section-kicker\">More case studies</p>")
                appendLine("                <h2 class=\"section-title\">Other projects</h2>")
                appendLine("            </div>")
                appendLine("            <div class=\"projects-grid\">")
                otherProjects.forEach { related ->
                    appendLine("                <a href=\"/projects/${related.slug}\" class=\"project-card personal-project-card cluster-card\">")
                    appendLine("                    <div class=\"project-content\">")
                    appendLine("                        <div class=\"project-header\">")
                    appendLine("                            <h3 class=\"project-title\">${related.name}</h3>")
                    appendLine("                            <span class=\"project-category\">${related.category}</span>")
                    appendLine("                        </div>")
                    appendLine("                        <p class=\"project-description\">${related.summary}</p>")
                    appendLine("                        <div class=\"project-tech-stack\">")
                    related.techStack.split(" • ").forEach { tag ->
                        appendLine("                            <span class=\"tech-tag\">$tag</span>")
                    }
                    appendLine("                        </div>")
                    appendLine("                    </div>")
                    appendLine("                </a>")
                }
                appendLine("            </div>")
                appendLine("        </section>")
            }

            appendLine("        <section class=\"quote-section\">")
            appendLine("            <p class=\"quote-text\">\"Code for clarity, automate for consistency, and build for longevity.\"</p>")
            appendLine("            <span class=\"quote-author\">— ${personalInfo.name}</span>")
            appendLine("        </section>")
            appendLine("    </main>")
            appendLine("    <footer>")
            appendLine("        <p>© 2025 ${personalInfo.name}. Built with Kotlin, Ktor & Clean Architecture.</p>")
            appendLine("    </footer>")
            appendLine("</body>")
            appendLine("</html>")
        }
    }

    override fun renderError(errorMessage: String): String {
        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
            appendLine("<head>")
            appendLine("    <title>Error - Personal Website</title>")
            appendLine("    <link rel=\"stylesheet\" href=\"/static/css/style.css\" type=\"text/css\">")
            appendLine("    <meta charset=\"UTF-8\">")
            appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
            appendLine("</head>")
            appendLine("<body>")
            appendLine("    <main>")
            appendLine("        <div class=\"page-content\">")
            appendLine("            <h1>Error</h1>")
            appendLine("            <p>$errorMessage</p>")
            appendLine("        </div>")
            appendLine("    </main>")
            appendLine("</body>")
            appendLine("</html>")
        }
    }

    private fun renderExperienceCards(experiences: List<WorkExperience>): String {
        return buildString {
            experiences.forEach { experience ->
                appendLine("                    <article class=\"experience-card cluster-card\">")
                appendLine("                        <div class=\"experience-card__header\">")
                appendLine("                            <div>")
                appendLine("                                <p class=\"experience-position\">${experience.position}</p>")
                appendLine("                                <p class=\"experience-company\">${experience.company} • ${experience.period}</p>")
                appendLine("                            </div>")
                appendLine("                            <span class=\"experience-location\">${experience.location}</span>")
                appendLine("                        </div>")
                appendLine("                        <ul class=\"experience-highlights\">")
                experience.responsibilities.take(3).forEach { highlight ->
                    appendLine("                            <li class=\"experience-highlight\">$highlight</li>")
                }
                appendLine("                        </ul>")
                appendLine("                    </article>")
            }
        }
    }
}
