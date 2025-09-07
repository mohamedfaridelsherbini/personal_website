package com.personalwebsite.presentation.views

import com.personalwebsite.presentation.models.WebsiteViewModel
import kotlinx.html.*

/**
 * HTML implementation of WebsiteView
 * Follows Single Responsibility Principle - only handles HTML rendering
 * Follows Dependency Inversion Principle - implements the abstraction
 * Follows Open/Closed Principle - can be extended without modification
 */
class HtmlWebsiteView : WebsiteView {
    
    override fun render(viewModel: WebsiteViewModel): String {
        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
            appendLine("<head>")
            appendLine("    <meta charset=\"utf-8\"/>")
            appendLine("    <link crossorigin=\"\" href=\"https://fonts.gstatic.com/\" rel=\"preconnect\"/>")
            appendLine("    <link as=\"style\" href=\"https://fonts.googleapis.com/css2?display=swap&amp;family=Noto+Sans%3Awght%40400%3B500%3B700%3B900&amp;family=Spline+Sans%3Awght%40400%3B500%3B700\" onload=\"this.rel='stylesheet'\" rel=\"stylesheet\"/>")
            appendLine("    <title>${viewModel.personalInfo.name} - ${viewModel.personalInfo.title}</title>")
            appendLine("    <link rel=\"stylesheet\" href=\"/static/css/style.css\" type=\"text/css\">")
            appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
            appendLine("    <script src=\"/static/js/smooth-scroll.js\"></script>")
            appendLine("    <script>")
            appendLine("        function copyToClipboard(text) {")
            appendLine("            navigator.clipboard.writeText(text).then(function() {")
            appendLine("                alert('Copied to clipboard: ' + text);")
            appendLine("            });")
            appendLine("        }")
            appendLine("    </script>")
            appendLine("</head>")
            appendLine("<body class=\"bg-[#111714] font-['Spline_Sans']\">")
            
            // Header
            appendLine("    <header>")
            appendLine("        <div class=\"header-left\">")
            appendLine("            <svg class=\"logo\" fill=\"none\" viewBox=\"0 0 48 48\" xmlns=\"http://www.w3.org/2000/svg\">")
            appendLine("                <path clip-rule=\"evenodd\" d=\"M39.475 21.6262C40.358 21.4363 40.6863 21.5589 40.7581 21.5934C40.7876 21.655 40.8547 21.857 40.8082 22.3336C40.7408 23.0255 40.4502 24.0046 39.8572 25.2301C38.6799 27.6631 36.5085 30.6631 33.5858 33.5858C30.6631 36.5085 27.6632 38.6799 25.2301 39.8572C24.0046 40.4502 23.0255 40.7407 22.3336 40.8082C21.8571 40.8547 21.6551 40.7875 21.5934 40.7581C21.5589 40.6863 21.4363 40.358 21.6262 39.475C21.8562 38.4054 22.4689 36.9657 23.5038 35.2817C24.7575 33.2417 26.5497 30.9744 28.7621 28.762C30.9744 26.5497 33.2417 24.7574 35.2817 23.5037C36.9657 22.4689 38.4054 21.8562 39.475 21.6262ZM4.41189 29.2403L18.7597 43.5881C19.8813 44.7097 21.4027 44.9179 22.7217 44.7893C24.0585 44.659 25.5148 44.1631 26.9723 43.4579C29.9052 42.0387 33.2618 39.5667 36.4142 36.4142C39.5667 33.2618 42.0387 29.9052 43.4579 26.9723C44.1631 25.5148 44.659 24.0585 44.7893 22.7217C44.9179 21.4027 44.7097 19.8813 43.5881 18.7597L29.2403 4.41187C27.8527 3.02428 25.8765 3.02573 24.2861 3.36776C22.6081 3.72863 20.7334 4.58419 18.8396 5.74801C16.4978 7.18716 13.9881 9.18353 11.5858 11.5858C9.18354 13.988 7.18717 16.4978 5.74802 18.8396C4.58421 20.7334 3.72865 22.6081 3.36778 24.2861C3.02574 25.8765 3.02429 27.8527 4.41189 29.2403Z\" fill=\"currentColor\" fill-rule=\"evenodd\"></path>")
            appendLine("            </svg>")
            appendLine("            <h2 class=\"header-title\">${viewModel.personalInfo.name}</h2>")
            appendLine("        </div>")
            appendLine("        <div class=\"nav-links\">")
            appendLine("            <a class=\"nav-link\" href=\"#about\">About</a>")
            appendLine("            <a class=\"nav-link\" href=\"#skills\">Skills</a>")
            appendLine("            <a class=\"nav-link\" href=\"#personal-projects\">Projects</a>")
            appendLine("            <a class=\"nav-link\" href=\"#contact\">Contact</a>")
            appendLine("        </div>")
            appendLine("        <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"resume-button\" target=\"_blank\" download=\"Mohamed_ElSherbini_Resume.pdf\">Resume</a>")
            appendLine("    </header>")
            
            appendLine("    <main>")
            
            // Home Section
            appendLine("        <section class=\"hero\">")
            appendLine("            <div id=\"home\"></div>")
            appendLine("            <div class=\"hero-content\">")
            appendLine("                <h1 class=\"hero-title\">Crafting Exceptional Mobile Experiences</h1>")
            appendLine("                <p class=\"hero-subtitle\">As a Senior Android Engineer with 8+ years of expertise, I transform complex challenges into elegant solutions. Specializing in Kotlin, Jetpack Compose, and Clean Architecture, I deliver high-performance mobile applications that exceed user expectations and drive business success.</p>")
            appendLine("                <div style=\"margin-top: 48px; display: flex; align-items: center; gap: 16px;\">")
            appendLine("                    <a href=\"#personal-projects\" class=\"cta-button\">Explore My Work</a>")
            appendLine("                    <a href=\"#contact\" class=\"cta-button-secondary\">Get in Touch</a>")
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // What I Do Section
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
            
            // About Section
            appendLine("        <section class=\"page-section\">")
            appendLine("            <div id=\"about\"></div>")
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
            appendLine("                <div class=\"projects-grid\">")
            viewModel.workExperience.forEach { experience ->
                appendLine("                <a href=\"#\" class=\"project-card experience-card\">")
                appendLine("                    <div class=\"project-content\">")
                appendLine("                        <div class=\"project-header\">")
                appendLine("                            <h3 class=\"project-title\">${experience.position}</h3>")
                appendLine("                            <span class=\"project-category\">${experience.location}</span>")
                appendLine("                        </div>")
                appendLine("                        <p class=\"project-description\">${experience.company} • ${experience.period}</p>")
                appendLine("                        <div class=\"project-tech-stack\">")
                experience.responsibilities.take(3).forEach { responsibility ->
                    appendLine("                            <span class=\"tech-tag\">${responsibility.take(50)}${if (responsibility.length > 50) "..." else ""}</span>")
                }
                appendLine("                        </div>")
                appendLine("                    </div>")
                appendLine("                </a>")
            }
            appendLine("                </div>")
            appendLine("            </div>")
            appendLine("        </section>")
            
            // Skills Section
            appendLine("        <section class=\"page-section\">")
            appendLine("            <div id=\"skills\"></div>")
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
            appendLine("        <section class=\"page-section\">")
            appendLine("            <div id=\"personal-projects\"></div>")
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
            appendLine("        <section class=\"page-section\">")
            appendLine("            <div id=\"contact\"></div>")
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
}
