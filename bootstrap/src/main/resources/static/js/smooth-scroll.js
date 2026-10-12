// Smooth scrolling, active link highlighting, and mobile navigation handling
document.addEventListener('DOMContentLoaded', function() {
    const anchorLinks = document.querySelectorAll('a[href^="#"]');
    const navToggle = document.querySelector('[data-nav-toggle]');
    const navPanel = document.querySelector('[data-nav-panel]');
    const navLinks = document.querySelectorAll('nav a[href^="#"]');
    const sections = document.querySelectorAll('[data-scroll-target]');
    const reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)');
    const projectCards = document.querySelectorAll('[data-project-reveal]');
    if (!reducedMotion.matches && 'IntersectionObserver' in window) {
        const observer = new IntersectionObserver(entries => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.remove('project-reveal-pending');
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.08 });
        projectCards.forEach(card => {
            card.classList.add('project-reveal-pending');
            observer.observe(card);
        });
        reducedMotion.addEventListener('change', () => {
            if (reducedMotion.matches) {
                observer.disconnect();
                projectCards.forEach(card => card.classList.remove('project-reveal-pending'));
            }
        });
    }

    function closeNavPanel() {
        if (!navPanel) return;
        navPanel.classList.remove('is-open');
        if (navToggle) {
            navToggle.setAttribute('aria-expanded', 'false');
        }
    }

    if (navToggle && navPanel) {
        navToggle.addEventListener('click', function() {
            const isOpen = navPanel.classList.toggle('is-open');
            navToggle.setAttribute('aria-expanded', String(isOpen));
        });
    }

    anchorLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();

            const targetId = this.getAttribute('href').substring(1);
            const targetElement = document.getElementById(targetId);

            if (targetElement) {
                const headerHeight = document.querySelector('header').offsetHeight;
                const targetPosition = targetElement.offsetTop - headerHeight - 20;

                window.scrollTo({
                    top: targetPosition,
                    behavior: reducedMotion.matches ? 'auto' : 'smooth'
                });
            }

            if (this.classList.contains('nav-link')) {
                closeNavPanel();
            }
        });
    });

    function updateActiveNav() {
        let current = '';
        const scrollPosition = window.scrollY;

        sections.forEach(section => {
            const sectionTop = section.offsetTop;
            if (scrollPosition >= sectionTop - 240) {
                current = section.getAttribute('id');
            }
        });

        navLinks.forEach(link => {
            link.classList.toggle('active', link.getAttribute('href') === '#' + current);
        });
    }

    window.addEventListener('scroll', updateActiveNav);
    window.addEventListener('resize', function() {
        if (window.innerWidth > 960) {
            closeNavPanel();
        }
    });

    document.addEventListener('keyup', function(event) {
        if (event.key === 'Escape') {
            closeNavPanel();
        }
    });

    updateActiveNav();
});
