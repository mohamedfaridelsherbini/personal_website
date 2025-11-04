package com.personalwebsite.presentation.views

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
        appendLine("                <div class=\"hero-text\">")
        appendLine("                    <p class=\"hero-eyebrow\">Munich · Kotlin architect</p>")
        appendLine("                    <h1 class=\"hero-title\">Designing resilient fintech apps for a neon future</h1>")
        appendLine("                    <p class=\"hero-subtitle\">I lead Android squads across Europe, blending Kotlin, Jetpack Compose, and automated pipelines to ship pixel-perfect journeys for millions of users. Think cyberpunk aesthetics, bank-grade reliability.</p>")
        appendLine("                    <div class=\"hero-actions\">")
        appendLine("                        <a href=\"#contact\" class=\"primary-button\">Partner with me</a>")
        appendLine("                        <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"secondary-button\" target=\"_blank\" rel=\"noopener\">Download résumé</a>")
        appendLine("                    </div>")
        appendLine("                    <div class=\"hero-status\">")
        appendLine("                        <span class=\"hero-status-pill\">Q1 2026 availability</span>")
        appendLine("                        <p>Currently scaling the Check24 mobile platform. Open to remote leadership, audits, and rapid strike consulting missions.</p>")
        appendLine("                    </div>")
        appendLine("                </div>")
        appendLine("                <div class=\"hero-panels\">")
        appendLine("                    <div class=\"hero-panel\">")
        appendLine("                        <h3>Signal boost</h3>")
        appendLine("                        <div class=\"hero-metrics\">")
        appendLine("                            <div>")
        appendLine("                                <div class=\"hero-metric-value\">8+</div>")
        appendLine("                                <div class=\"hero-metric-label\">Years shipping Android</div>")
        appendLine("                            </div>")
        appendLine("                            <div>")
        appendLine("                                <div class=\"hero-metric-value\">20</div>")
        appendLine("                                <div class=\"hero-metric-label\">Apps in production</div>")
        appendLine("                            </div>")
        appendLine("                            <div>")
        appendLine("                                <div class=\"hero-metric-value\">4</div>")
        appendLine("                                <div class=\"hero-metric-label\">Markets led</div>")
        appendLine("                            </div>")
        appendLine("                            <div>")
        appendLine("                                <div class=\"hero-metric-value\">99%</div>")
        appendLine("                                <div class=\"hero-metric-label\">Crash-free sessions</div>")
        appendLine("                            </div>")
        appendLine("                        </div>")
        appendLine("                    </div>")
        appendLine("                    <div class=\"hero-panel hero-signal\">")
        appendLine("                        <h3>Systems online</h3>")
        appendLine("                        <p>Composable design systems, real-time observability, and battle-tested release trains keep every build stable under neon lights.</p>")
        appendLine("                        <ul class=\"hero-list\">")
        appendLine("                            <li>Jetpack Compose design language rolled out to 6+ squads</li>")
        appendLine("                            <li>Jenkins pipelines with Docker, Gradle remote cache, and instant rollbacks</li>")
        appendLine("                            <li>Security reviews covering encryption, biometric flows, and fintech compliance</li>")
        appendLine("                        </ul>")
        appendLine("                        <div class=\"hero-tag-cloud\">")
        appendLine("                            <span class=\"hero-tag\">Kotlin</span>")
        appendLine("                            <span class=\"hero-tag\">Compose</span>")
        appendLine("                            <span class=\"hero-tag\">KMP</span>")
        appendLine("                            <span class=\"hero-tag\">CI/CD</span>")
        appendLine("                        </div>")
        appendLine("                    </div>")
        appendLine("                </div>")
        appendLine("            </div>")
        appendLine("        </section>")
            
            // What I Do Section - might add more features later
            appendLine("        <section class=\"features\">")
            appendLine("            <div class=\"feature-grid\">")
            appendLine("                <div class=\"feature\">")
            appendLine("                    <div class=\"feature-icon\">⚙️</div>")
            appendLine("                    <h3>Composable architecture</h3>")
            appendLine("                    <p>Radically modular Kotlin base layers, sealed contracts, and Compose-first UI kits so every squad ships independently without breaking the core system.</p>")
            appendLine("                </div>")
            appendLine("                <div class=\"feature\">")
            appendLine("                    <div class=\"feature-icon\">📡</div>")
            appendLine("                    <h3>Observability & automation</h3>")
            appendLine("                    <p>CI pipelines with Jenkins, Docker, and Gradle remote cache, paired with crash analytics and synthetic monitoring that surface issues before users notice.</p>")
            appendLine("                </div>")
            appendLine("                <div class=\"feature\">")
            appendLine("                    <div class=\"feature-icon\">🔒</div>")
            appendLine("                    <h3>Fintech-grade reliability</h3>")
            appendLine("                    <p>Biometric flows, encryption layers, and compliance-ready data handling built directly into the design system so features stay secure by default.</p>")
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
            appendLine("                <p class=\"about-description\">I’m a Munich-based Android lead obsessed with building resilient fintech platforms. Over the past eight years I’ve led distributed squads across Europe and MENA, rolling out Kotlin-first architectures, Compose design systems, and privacy-first user journeys that keep regulators and users equally happy.</p>")
            appendLine("                <p class=\"about-description\">My work blends deep technical rigor with product instincts: multi-module codebases, biometric security flows, experiment platforms, and delivery pipelines that stay green even when the lights are neon. I translate ambiguous roadmaps into confident launch plans for founders, banks, and scale-ups.</p>")
            appendLine("                <ul class=\"about-points\">")
            appendLine("                    <li>Scaled a single Android app into a multi-brand platform powering millions of monthly sessions.</li>")
            appendLine("                    <li>Set up observability stacks (Crashlytics, Grafana, Datadog) to keep KPIs visible in real time.</li>")
            appendLine("                    <li>Mentored squads through Compose, KMP, and accessibility migrations without slowing releases.</li>")
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
                skill.items.forEach { item ->
                    appendLine("                    <div class=\"skill-tag\">$item</div>")
                }
            }
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            
            // Personal Projects Section
            appendLine("        <section class=\"page-section\" id=\"personal-projects\" data-scroll-target=\"personal-projects\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                <div class=\"section-heading\">")
            appendLine("                    <p class=\"section-kicker\">Lab notes</p>")
            appendLine("                    <h1 class=\"section-title\">Personal Projects</h1>")
            appendLine("                </div>")
            appendLine("                <p class=\"section-subtitle\">Playground explorations where I prototype new interaction models, SDKs, or automation tricks before they hit client work.</p>")
            appendLine("                <div class=\"projects-grid\">")
            viewModel.personalProjects.forEach { project ->
                appendLine("                    <a href=\"#\" class=\"project-card personal-project-card\">")
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
            
            appendLine("    </main>")
            appendLine("    <footer>")
            appendLine("        <p>© 2024 ${viewModel.personalInfo.name}. Built with Kotlin, Ktor & Clean Architecture.</p>")
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
                appendLine("                    <article class=\"experience-card\">")
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
