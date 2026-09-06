---
version: alpha
name: Datasheet
description: Personal portfolio for Mohamed ElSherbini — a light engineering-drawing UI. Paper ground, hairline rules, square corners, mono annotation labels, and a blueprint dimension-line spine down the career timeline.
colors:
  paper: "#f2efe6"
  paper-bright: "#faf8f2"
  paper-dim: "#e7e1d1"
  ink: "#14171a"
  ink-soft: "#4b5157"
  ink-faint: "#868d94"
  blueprint: "#2456a8"
  blueprint-soft: "#6f8fc4"
  signal-orange: "#e8631c"
  signal-orange-soft: "#f4a06e"
  line: "rgba(20,23,26,0.16)"
  line-strong: "rgba(20,23,26,0.32)"
  grid-dot: "rgba(36,86,168,0.14)"
  wire-copper: "#a85a1c"
  wire-blue: "#2456a8"
  wire-teal: "#0f7a6c"
  wire-violet: "#63488f"
  wire-amber: "#a5760a"
  wire-red: "#a52f1f"
  wire-green: "#3f7d3a"
  wire-gray: "#5b6570"
  theme-meta: "#f2efe6"
typography:
  display-hero:
    fontFamily: Big Shoulders Display
    fontSize: clamp(52px, 6.5vw, 92px)
    fontWeight: "900"
    lineHeight: 0.98
    letterSpacing: 0
    textTransform: uppercase
  display-section:
    fontFamily: Big Shoulders Display
    fontSize: clamp(38px, 5vw, 56px)
    fontWeight: "900"
    lineHeight: 1
    textTransform: uppercase
  headline-md:
    fontFamily: Big Shoulders Display
    fontSize: 22px
    fontWeight: "800"
    lineHeight: 1.15
    textTransform: uppercase
  body-lg:
    fontFamily: IBM Plex Sans
    fontSize: 17px
    fontWeight: "400"
    lineHeight: 1.7
  body-md:
    fontFamily: IBM Plex Sans
    fontSize: 15px
    fontWeight: "400"
    lineHeight: 1.6
  label-mono:
    fontFamily: IBM Plex Mono
    fontSize: 12px
    fontWeight: "600"
    letterSpacing: 0.08em
    textTransform: uppercase
  mono-md:
    fontFamily: IBM Plex Mono
    fontSize: 13px
    fontWeight: "400"
    lineHeight: 1.5
rounded:
  none: 0px
spacing:
  xs: 8px
  sm: 16px
  md: 24px
  lg: 32px
  xl: 40px
  section: 64px
  container-inline: 40px
components:
  header-bar:
    backgroundColor: "rgba(250,248,242,0.94)"
    textColor: "{colors.ink}"
    height: 64px
    padding: "14px {spacing.container-inline}"
  cluster-card:
    backgroundColor: "{colors.paper-bright}"
    textColor: "{colors.ink}"
    rounded: "{rounded.none}"
    padding: "{spacing.lg}"
    border: "1px solid {colors.line}"
    shadow: "hard offset, no blur"
  button-primary:
    backgroundColor: "{colors.ink}"
    textColor: "{colors.paper-bright}"
    typography: "{typography.label-mono}"
    rounded: "{rounded.none}"
    height: 36px
    padding: "0 16px"
  button-primary-hover:
    backgroundColor: "{colors.signal-orange}"
  button-accent:
    backgroundColor: "{colors.signal-orange}"
    textColor: "{colors.paper-bright}"
    typography: "{typography.label-mono}"
    rounded: "{rounded.none}"
    padding: "0 18px"
  contact-card:
    backgroundColor: "{colors.paper-bright}"
    textColor: "{colors.ink}"
    rounded: "{rounded.none}"
    borderTop: "3px solid {colors.blueprint}"
    padding: "{spacing.lg}"
  skill-cat-languages:
    textColor: "{colors.wire-blue}"
  skill-cat-mobile-architecture:
    textColor: "{colors.wire-teal}"
  skill-cat-testing-automation:
    textColor: "{colors.wire-amber}"
  skill-cat-backend-devops:
    textColor: "{colors.wire-violet}"
  skill-cat-frameworks-sdks:
    textColor: "{colors.wire-red}"
  skill-cat-tools-collaboration:
    textColor: "{colors.wire-copper}"
  skill-cat-other-skills:
    textColor: "{colors.wire-green}"
  category-sports-fantasy:
    textColor: "{colors.wire-red}"
  category-enterprise:
    textColor: "{colors.wire-teal}"
  category-consumer-app:
    textColor: "{colors.wire-blue}"
  category-social-app:
    textColor: "{colors.wire-blue}"
  category-wellness:
    textColor: "{colors.wire-green}"
  category-library:
    textColor: "{colors.wire-violet}"
  category-automation-devops:
    textColor: "{colors.wire-amber}"
  category-open-source:
    textColor: "{colors.wire-copper}"
  skip-link:
    backgroundColor: "{colors.signal-orange}"
    textColor: "{colors.paper-bright}"
    rounded: "{rounded.none}"
    padding: "10px 14px"
  admin-panel:
    backgroundColor: "#0a0c10"
    textColor: "#e4ecff"
    rounded: "8px"
    padding: "{spacing.md}"
  admin-button:
    backgroundColor: "linear-gradient(135deg, #00e0ff, #7c3aed)"
    textColor: "#0a0c10"
    rounded: "8px"
    padding: "12px 16px"
---

## Overview

**Datasheet** is the visual identity for Mohamed ElSherbini's personal portfolio (Kotlin + Ktor, static CSS). The subject is a Senior Android Engineer who also builds circuit boards and embedded hardware (Arduino, PCBs, biometric SDKs) — the design borrows the vocabulary of an engineering datasheet or technical drawing rather than a generic dark developer-portfolio dashboard: light paper ground, hairline rules, square corners, mono annotation labels.

**Brand keywords:** drafting precision, schematic, technical-drawing, calm paper, engineer-not-hacker.

**Signature element:** the career timeline (`.timeline`) is drawn as a blueprint "dimension line" — a solid blueprint-blue rule running down the page with diamond via-markers at each role, the current role lit in signal-orange instead of blueprint (`.timeline-item--current`, set in `ExperienceCardComponent.kt` when a role's period contains "Present"). Skills and project categories are coded with **wire colors** — a nod to resistor color-bands / wire codes — rather than a single rainbow of arbitrary accents.

**Implementation map:** tokens in this file are normative. Apply them in `bootstrap/src/main/resources/static/css/style.css`. HTML is rendered from `infrastructure/.../web/view/components/`. Keep `theme-color` meta (`PageHeadComponent.kt`) aligned with `{colors.paper}`.

**Format reference:** [google-labs-code/design.md](https://github.com/google-labs-code/design.md). Validate with `npx @google/design.md lint DESIGN.md`.

## Colors

Warm paper canvas with near-black ink text, a single **blueprint-blue** structural color (rules, links, the timeline spine) and a single **signal-orange** interaction accent (CTAs, hover states, the current role). Wire colors are semantic category coding only — never used for body text or structure.

| Token | Hex | Role |
|-------|-----|------|
| `paper` | `#f2efe6` | Page canvas |
| `paper-bright` | `#faf8f2` | Cards, raised surfaces |
| `paper-dim` | `#e7e1d1` | Tags, recessed fills |
| `ink` / `ink-soft` / `ink-faint` | `#14171a` / `#4b5157` / `#868d94` | Headlines / body / captions |
| `blueprint` | `#2456a8` | Structural rules, links, timeline spine |
| `signal-orange` | `#e8631c` | CTAs, hover, current-role marker |
| `wire-*` | see YAML | Skill/project category coding only |

**No glass, no blur, no glow.** Depth comes from hairline borders plus a hard 3–7px offset shadow (`--shadow-sm/md/lg`), like a stacked index card — never `backdrop-filter` or soft blurred shadows.

**Admin panel** keeps its separate dark shell (`#0a0c10`, cyan→violet gradient) — it is an internal tool, not part of the public brand.

## Typography

Google Fonts loaded in CSS: **Big Shoulders Display** (condensed industrial caps — headings only, used with restraint), **IBM Plex Sans** (body), **IBM Plex Mono** (labels, dates, tags, the "silkscreen" voice).

All headings render uppercase via the global `h1–h6` rule. Body copy stays sentence case. Mono is reserved for: nav links, buttons, chips/tags, dates (`[ SEP 2022 – PRESENT ]`), section kickers, and captions — never for paragraph copy.

Do not load extra families in `PageHeadComponent` without updating this file.

## Layout

- **Max content width:** ~1200px centered sections; hero may be full-bleed.
- **Grid base:** 8px; common gaps: 16px (tight), 24px (cards), 32px (project groups).
- **Section rhythm:** ~96px vertical spacing between major blocks (`.page-section`).
- **Header:** fixed, ~64px tall, horizontal padding `container-inline` (40px desktop; 18–24px mobile).
- **Breakpoints:** stack nav under ~960px; single-column grids under ~768px.
- **Background texture:** a faint blueprint-dot grid (`radial-gradient` dots, 28px pitch) across the whole page — the only ambient texture; keep it subtle.
- **Safe areas:** respect `prefers-reduced-motion` — disable hover lifts.

## Elevation & Depth

| Level | Treatment |
|-------|-----------|
| 0 – Canvas | `paper` + faint dot grid |
| 1 – Card | `paper-bright`, 1px `line` border, hard offset shadow `shadow-sm` |
| 2 – Hover | `translate(-2px, -2px)`, shadow steps up to `shadow-md`, border/accent to `signal-orange` or the active wire color |
| 3 – Header | `paper-bright` at 94% opacity, 1px `line-strong` bottom border, no blur |

CSS variables `--shadow-sm/md/lg` and `--signal-orange`/`--blueprint` are defined in `style.css` `:root`.

## Shapes

**Square corners everywhere** (`border-radius: 0`) — the drafting-precision signature. The only rounded exceptions are inherited browser defaults (none currently used). Do not reintroduce pill shapes or soft radii; if a control needs to look "friendly," use a hairline border and a wire-color accent instead of rounding it.

## Components

### Header (`header-bar`, `nav-link`)

Fixed top bar, paper-bright, 1px bottom rule. Nav links are mono uppercase with an underline that appears in signal-orange on hover/active — no pill backgrounds.

### Cluster card (`cluster-card`)

Primary content container for hero panels, project detail sections. Paper-bright, 1px hairline border, hard offset shadow, square corners. No `::before` glow — depth is the offset shadow only.

### Timeline (`.timeline`, `.timeline-item`)

The signature device. 2px blueprint rule as the spine; each `.timeline-item` gets a rotated-square (diamond) via-marker. `.timeline-item--current` (added in Kotlin when `period` contains "Present") lights the marker and the bracketed date in signal-orange instead of blueprint.

### Skill / project wire coding (`skill-cat-*`, `category-*`)

Map JSON `category` to one `--wire-color` custom property per group, used for the group title and the hover/border accent on tags and cards. Never mix two wire colors within one group.

### Contact grid (`contact-card`)

Equal-height cards, top border in a rotating wire color (blueprint / teal / signal-orange), `auto-fit` grid.

### Admin (`admin-panel`, `admin-button`)

Separate dark shell for `/admin`; monospace JSON textareas. Not required to match the public Datasheet system.

## Do's and Don'ts

**Do**

- Centralize colors in `:root` matching YAML tokens; reference `var(--*)` everywhere.
- Keep exactly two structural/interaction colors (`blueprint`, `signal-orange`); use `wire-*` only for category coding.
- Keep every corner square; use hairline rules and hard offset shadows for depth.
- Honor `prefers-reduced-motion` and visible `:focus-visible` outlines (signal-orange, 2px).
- Update snapshot tests after visual HTML changes (`UPDATE_SNAPSHOTS=true ./gradlew test`).

**Don't**

- Introduce new font families without updating this file.
- Reintroduce `backdrop-filter`, blurred glow shadows, or pill/rounded shapes on public pages.
- Use more than one wire color inside a single category group.
- Hard-code colors in Kotlin HTML builders — prefer CSS classes.
- Ship undefined CSS variables without definitions in `style.css` `:root`.
