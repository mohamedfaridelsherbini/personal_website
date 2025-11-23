@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.application.website.model.PageMetadata
import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Shared HTML head rendering for both home and project pages.
 */
class PageHeadComponent {
    fun appendHomeHead(
        builder: StringBuilder,
        metadata: PageMetadata,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("<head>")
        appendCommonHead(builder, metadata)
        builder.appendLine("    <meta name=\"author\" content=\"${personalInfo.name}\">")
        metadata.publishedTime?.let {
            builder.appendLine("    <meta property=\"article:published_time\" content=\"$it\">")
        }
        builder.appendLine("    <meta property=\"og:image:width\" content=\"800\">")
        builder.appendLine("    <meta property=\"og:image:height\" content=\"800\">")
        builder.appendLine("    <meta property=\"og:image:type\" content=\"image/jpeg\">")
        builder.appendLine("    <meta property=\"og:image:alt\" content=\"Portrait of ${personalInfo.name}\">")
        builder.appendLine("    <meta name=\"twitter:image:alt\" content=\"Portrait of ${personalInfo.name}\">")
        builder.appendLine("    <script src=\"/static/js/smooth-scroll.js\"></script>")
        builder.appendLine("    <script src=\"/static/js/cursor-buddy.js\"></script>")
        metadata.structuredDataJsonLd?.let { jsonLd ->
            builder.appendLine("    <script type=\"application/ld+json\">")
            builder.appendLine(jsonLd)
            builder.appendLine("    </script>")
        }
        builder.appendLine("    <script>")
        builder.appendLine("        function copyToClipboard(text) {")
        builder.appendLine("            navigator.clipboard.writeText(text).then(function() {")
        builder.appendLine("                alert('Copied to clipboard: ' + text);")
        builder.appendLine("            });")
        builder.appendLine("        }")
        builder.appendLine("    </script>")
        builder.appendLine("</head>")
    }

    fun appendProjectHead(
        builder: StringBuilder,
        metadata: PageMetadata,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("<head>")
        appendCommonHead(builder, metadata)
        builder.appendLine("    <meta property=\"og:image:alt\" content=\"${personalInfo.name} case study\">")
        metadata.structuredDataJsonLd?.let { jsonLd ->
            builder.appendLine("    <script type=\"application/ld+json\">")
            builder.appendLine(jsonLd)
            builder.appendLine("    </script>")
        }
        builder.appendLine("</head>")
    }

    private fun appendCommonHead(
        builder: StringBuilder,
        metadata: PageMetadata,
    ) {
        val encodedDescription = metadata.description.escapeHtmlAttribute()
        builder.appendLine("    <meta charset=\"utf-8\"/>")
        builder.appendLine("    <link crossorigin=\"\" href=\"https://fonts.gstatic.com/\" rel=\"preconnect\"/>")
        builder.appendLine(
            "    <link as=\"style\" href=\"https://fonts.googleapis.com/css2?display=swap&amp;family=Noto+Sans%3Awght%40400%3B500%3B700%3B900&amp;family=Spline+Sans%3Awght%40400%3B500%3B700\" onload=\"this.rel='stylesheet'\" rel=\"stylesheet\"/>",
        )
        builder.appendLine("    <meta name=\"google-site-verification\" content=\"Va2vDXEdiRi_uhOL64PstYFMmbfnEO0xCMvhsqLSWCQ\"/>")
        builder.appendLine("    <title>${metadata.title}</title>")
        builder.appendLine("    <link rel=\"icon\" type=\"image/png\" sizes=\"32x32\" href=\"/static/images/favicon-portrait.png?v=2\">")
        builder.appendLine("    <link rel=\"icon\" type=\"image/png\" sizes=\"64x64\" href=\"/static/images/favicon-portrait.png?v=2\">")
        builder.appendLine("    <link rel=\"apple-touch-icon\" sizes=\"180x180\" href=\"/static/images/favicon-portrait.png?v=2\">")
        builder.appendLine("    <link rel=\"icon\" type=\"image/x-icon\" href=\"/static/images/favicon.ico?v=2\">")
        builder.appendLine("    <link rel=\"stylesheet\" href=\"/static/css/style.css\" type=\"text/css\">")
        builder.appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
        builder.appendLine("    <meta name=\"description\" content=\"$encodedDescription\">")
        builder.appendLine("    <link rel=\"canonical\" href=\"${metadata.canonicalUrl}\">")
        builder.appendLine("    <meta property=\"og:type\" content=\"${metadata.ogType}\">")
        builder.appendLine("    <meta property=\"og:title\" content=\"${metadata.title}\">")
        builder.appendLine("    <meta property=\"og:description\" content=\"$encodedDescription\">")
        builder.appendLine("    <meta property=\"og:url\" content=\"${metadata.canonicalUrl}\">")
        builder.appendLine("    <meta property=\"og:image\" content=\"${metadata.socialImageUrl}\">")
        builder.appendLine("    <meta name=\"twitter:card\" content=\"${metadata.twitterCard}\">")
        builder.appendLine("    <meta name=\"twitter:title\" content=\"${metadata.title}\">")
        builder.appendLine("    <meta name=\"twitter:description\" content=\"$encodedDescription\">")
        builder.appendLine("    <meta name=\"twitter:image\" content=\"${metadata.socialImageUrl}\">")
    }

    private fun String.escapeHtmlAttribute(): String = replace("\"", "&quot;")
}
