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
            appendLine("    <link rel=\"icon\" type=\"image/png\" href=\"/static/images/favicon.png\"\">")
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
            appendLine("            <img src=\"/static/images/logo-badge.svg\" alt=\"Site logo\" class=\"logo-badge\">")
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
            appendLine("            <div class=\"hero-background\"></div>")
            appendLine("            <div class=\"hero-overlay\"></div>")
            appendLine("            <div class=\"hero-content\">")
            appendLine("                <div class=\"hero-text\">")
            appendLine("                    <h1 class=\"hero-title\">Crafting Exceptional Mobile Experiences</h1>")
            appendLine("                    <p class=\"hero-subtitle\">As a Senior Android Engineer with 8+ years of expertise, I transform complex challenges into elegant solutions. Specializing in Kotlin, Jetpack Compose, and Clean Architecture, I deliver high-performance mobile applications that exceed user expectations and drive business success.</p>")
            appendLine("                    <div class=\"hero-actions\">")
            appendLine("                        <a href=\"#contact\" class=\"primary-button\">Schedule a call</a>")
            appendLine("                        <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"secondary-button\" target=\"_blank\">Download resume</a>")
            appendLine("                        <a href=\"#personal-projects\" class=\"ghost-button\">Explore projects</a>")
            appendLine("                    </div>")
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // What I Do Section - might add more features later
            appendLine("        <section class=\"features\">")
            appendLine("            <div class=\"feature-grid\">")
            appendLine("                <div class=\"feature\">")
            appendLine("                    <h3>Clean Architecture</h3>")
            appendLine("                    <p>By separating concerns and creating a modular structure, I build applications that are easy to test, debug, and evolve over time.</p>")
            appendLine("                </div>")
            appendLine("                <div class=\"feature\">")
            appendLine("                    <h3>Responsive Design</h3>")
            appendLine("                    <p>From mobile to desktop, I ensure that every user has a seamless and engaging experience, regardless of their device.</p>")
            appendLine("                </div>")
            appendLine("                <div class=\"feature\">")
            appendLine("                    <h3>Performance</h3>")
            appendLine("                    <p>I optimize for speed and efficiency, delivering fast load times and smooth interactions that enhance user satisfaction.</p>")
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // About Section - this section took the most time to get right
            appendLine("        <section class=\"page-section\" id=\"about\" data-scroll-target=\"about\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                                <div class=\"about-header\">")
                appendLine("                    <h1 class=\"about-name\">${viewModel.personalInfo.name}</h1>")
                appendLine("                    <p class=\"about-title\">${viewModel.personalInfo.title}</p>")
                appendLine("                    <p class=\"about-location\">📍 ${viewModel.personalInfo.location}</p>")
                appendLine("                </div>")
            appendLine("                <h2 class=\"section-title\">About Me</h2>")
            appendLine("                <p class=\"about-description\">Senior Android Engineer with 8+ years of experience building robust mobile applications using Kotlin and Java, and beginner experience in iOS development with Swift. Specialized in Android development, Kotlin Multiplatform, and scalable multi-module projects.</p>")
            appendLine("                <p class=\"about-description\">Experienced in automation testing frameworks (Appium, Selenium), CI/CD pipelines with Docker & Jenkins, and backend integration with Spring Boot microservices. Adept at delivering high-quality, product-focused solutions in Agile, cross-functional teams across Europe and MENA.</p>")
            
            // Work Experience
            appendLine("                <h2 class=\"section-title\">Experience</h2>")
            appendLine("                <div class=\"experience-grid\">")
            append(renderExperienceCards(viewModel.workExperience))
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // Skills Section
            appendLine("        <section class=\"page-section\" id=\"skills\" data-scroll-target=\"skills\">")
            appendLine("            <div class=\"page-content\">")
            appendLine("                <h1 class=\"section-title\">Technical Skills</h1>")
            appendLine("                <p class=\"section-subtitle\">A comprehensive overview of my technical expertise and tools I work with.</p>")
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
            appendLine("                <h1 class=\"section-title\">Personal Projects</h1>")
            appendLine("                <p class=\"section-subtitle\">Side projects and personal development work.</p>")
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
            appendLine("                <h1 class=\"section-title\">Get In Touch</h1>")
            appendLine("                <p class=\"section-subtitle\">Let's connect and discuss opportunities.</p>")
            appendLine("                <div class=\"contact-info\">")
            appendLine("                    <a href=\"mailto:m.farid.shawky@gmail.com\" class=\"contact-item contact-button\">")
            appendLine("                        <h3>✉️ Email</h3>")
            appendLine("                    </a>")
            appendLine("                    <a href=\"${viewModel.personalInfo.linkedin}\" target=\"_blank\" class=\"contact-item contact-button\">")
            appendLine("                        <h3>💼 LinkedIn</h3>")
            appendLine("                    </a>")
            appendLine("                    <a href=\"${viewModel.personalInfo.github}\" target=\"_blank\" class=\"contact-item contact-button\">")
            appendLine("                        <h3>💻 GitHub</h3>")
            appendLine("                    </a>")
            appendLine("                </div>")
            
            // Languages
            appendLine("                <div class=\"languages-section\">")
            appendLine("                    <h2 class=\"section-title\">Languages</h2>")
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
