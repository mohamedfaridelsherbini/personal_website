package tools

import com.personalwebsite.application.website.model.PageMetadata
import com.personalwebsite.application.website.model.PageModel
import com.personalwebsite.application.website.model.WebsiteViewModel
import com.personalwebsite.infrastructure.web.view.HtmlWebsiteView
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val viewModel = WebsiteViewModel(
        personalInfo = personalInfo,
        skills = skills,
        workExperience = workExperience,
        personalProjects = projects,
        languages = languages
    )

    val baseUrl = "https://www.mohamedfaridelsherbini.com"
    val socialImageUrl = "$baseUrl/static/images/profile-social.jpg"

    val homeMetadata = PageMetadata(
        title = "${personalInfo.name} - ${personalInfo.title}",
        description = personalInfo.summary,
        canonicalUrl = "$baseUrl/",
        socialImageUrl = socialImageUrl,
        ogType = "website",
        publishedTime = "2025-11-02T00:00:00+01:00",
        structuredDataJsonLd = createPersonSchema(personalInfo, baseUrl, socialImageUrl, personalInfo.summary)
    )

    val view = HtmlWebsiteView()
    val homePage = PageModel.Home(homeMetadata, viewModel)
    writeSnapshot("home", view.render(homePage))

    val featuredProject = projects.first { it.slug == "spl-fantasy" }
    val projectMetadata = PageMetadata(
        title = "${featuredProject.name} – ${personalInfo.name}",
        description = featuredProject.summary,
        canonicalUrl = "$baseUrl/projects/${featuredProject.slug}",
        socialImageUrl = socialImageUrl,
        ogType = "article",
        structuredDataJsonLd = createProjectSchema(featuredProject, "$baseUrl/projects/${featuredProject.slug}", personalInfo.name)
    )

    val projectPage = PageModel.Project(
        project = featuredProject,
        metadata = projectMetadata,
        site = viewModel
    )
    writeSnapshot("project-spl-fantasy", view.render(projectPage))
}

private fun writeSnapshot(name: String, content: String) {
    val path = Path.of("src/test/resources/snapshots/$name.html")
    Files.createDirectories(path.parent)
    Files.writeString(path, content)
}

private fun createPersonSchema(
    personalInfo: com.personalwebsite.domain.entities.PersonalInfo,
    baseUrl: String,
    socialImageUrl: String,
    description: String
): String {
    return """
        {
          \"@context\": \"https://schema.org\",
          \"@type\": \"Person\",
          \"name\": \"${personalInfo.name}\",
          \"jobTitle\": \"${personalInfo.title}\",
          \"description\": \"${description.replace("\"", "\\\\\"")}\",
          \"email\": \"mailto:${personalInfo.email}\",
          \"telephone\": \"${personalInfo.phone}\",
          \"url\": \"$baseUrl\",
          \"image\": \"$socialImageUrl\",
          \"sameAs\": [
            \"${personalInfo.linkedin}\",
            \"${personalInfo.github}\"
          ],
          \"address\": {
            \"@type\": \"PostalAddress\",
            \"addressLocality\": \"${personalInfo.location}\"
          }
        }
    """.trimIndent()
}

private fun createProjectSchema(
    project: com.personalwebsite.domain.entities.PersonalProject,
    canonicalUrl: String,
    authorName: String
): String {
    return """
        {
          \"@context\": \"https://schema.org\",
          \"@type\": \"CreativeWork\",
          \"name\": \"${project.name}\",
          \"description\": \"${project.summary.replace("\"", "\\\\\"")}\",
          \"url\": \"$canonicalUrl\",
          \"author\": {
            \"@type\": \"Person\",
            \"name\": \"$authorName\"
          },
          \"genre\": \"${project.category}\",
          \"headline\": \"${project.name}\"
        }
    """.trimIndent()
}
