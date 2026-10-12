// Contact companion: docked avatar with contact menu, plus a bounded welcome shown once per tab session.
(function () {
    'use strict';

    var root = document.querySelector('[data-companion]');
    if (!root) return;

    var button = root.querySelector('[data-companion-button]');
    var popover = root.querySelector('[data-companion-popover]');
    var avatar = root.querySelector('[data-companion-avatar]');
    var SHEET = '/static/images/companion-sheet.webp';
    var STORAGE_KEY = 'pw-welcome-seen';
    var reducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
    var restExpression = 'neutral';

    var reactionTimer = null;
    var welcoming = false;

    function setExpression(name) {
        avatar.setAttribute('data-expression', name);
    }

    // Short, user-triggered reactions that fall back to the resting expression. None run with reduced motion.
    function react(name, ms) {
        if (reducedMotion || welcoming) return;
        clearTimeout(reactionTimer);
        setExpression(name);
        reactionTimer = setTimeout(function () { setExpression(restExpression); }, ms);
    }

    function settle() {
        clearTimeout(reactionTimer);
        setExpression(restExpression);
    }

    function openMenu() {
        popover.hidden = false;
        button.setAttribute('aria-expanded', 'true');
        restExpression = 'happy';
        setExpression('happy');
    }

    function closeMenu(returnFocus) {
        popover.hidden = true;
        button.setAttribute('aria-expanded', 'false');
        restExpression = 'neutral';
        setExpression('neutral');
        if (returnFocus) button.focus();
    }

    var clicks = [];
    button.addEventListener('click', function () {
        var now = Date.now();
        clicks = clicks.filter(function (t) { return now - t < 1500; });
        clicks.push(now);
        if (clicks.length >= 4) {
            clicks = [];
            react('frustrated', 1800);
            return;
        }
        if (popover.hidden) openMenu(); else closeMenu(false);
    });
    button.addEventListener('pointerenter', function () { if (popover.hidden) react('curious', 4000); });
    button.addEventListener('pointerleave', function () { if (popover.hidden) settle(); });
    button.addEventListener('pointerdown', function () { if (popover.hidden) react('surprised', 600); });
    popover.addEventListener('click', function (event) {
        if (event.target.closest('a')) {
            closeMenu(false);
            react('excited', 1800);
        }
    });
    popover.querySelectorAll('a').forEach(function (link) {
        link.addEventListener('pointerenter', function () { react('wink', 4000); });
        link.addEventListener('pointerleave', settle);
    });
    document.addEventListener('keydown', function (event) {
        if (event.key === 'Escape' && !popover.hidden) closeMenu(true);
    });
    document.addEventListener('pointerdown', function (event) {
        if (!popover.hidden && !root.contains(event.target)) closeMenu(false);
    });

    // Reads thoughtful when the visitor reaches the career and skills sections, once each.
    if ('IntersectionObserver' in window && !reducedMotion) {
        var seen = new IntersectionObserver(function (entries) {
            entries.forEach(function (entry) {
                if (!entry.isIntersecting) return;
                seen.unobserve(entry.target);
                if (popover.hidden) react('thoughtful', 2500);
            });
        }, { threshold: 0.1 });
        ['experience', 'skills'].forEach(function (id) {
            var section = document.getElementById(id);
            if (section) seen.observe(section);
        });
    }

    // Falls asleep after a minute without input and wakes up surprised.
    var idleTimer = null;
    var asleep = false;
    function resetIdle() {
        if (reducedMotion) return;
        if (asleep) {
            asleep = false;
            restExpression = popover.hidden ? 'neutral' : 'happy';
            react('surprised', 800);
        }
        clearTimeout(idleTimer);
        idleTimer = setTimeout(function () {
            if (!popover.hidden || welcoming) return;
            asleep = true;
            restExpression = 'sleepy';
            setExpression('sleepy');
        }, 60000);
    }
    ['pointermove', 'keydown', 'scroll', 'touchstart'].forEach(function (type) {
        window.addEventListener(type, resetIdle, { passive: true });
    });
    resetIdle();

    // Concerned when the connection drops, sad if it stays down, happy when it returns.
    var offlineTimer = null;
    window.addEventListener('offline', function () {
        if (reducedMotion) return;
        restExpression = 'concerned';
        setExpression('concerned');
        offlineTimer = setTimeout(function () {
            restExpression = 'sad';
            setExpression('sad');
        }, 10000);
    });
    window.addEventListener('online', function () {
        if (reducedMotion) return;
        clearTimeout(offlineTimer);
        restExpression = popover.hidden ? 'neutral' : 'happy';
        react('happy', 1800);
    });

    function eligibleForWelcome() {
        if (root.getAttribute('data-welcome') !== 'true') return false;
        if (reducedMotion || window.location.hash) return false;
        if (window.innerWidth < 320 || window.innerHeight < 480) return false;
        try {
            if (window.sessionStorage.getItem(STORAGE_KEY)) return false;
            window.sessionStorage.setItem(STORAGE_KEY, '1');
        } catch (e) {
            return false;
        }
        return true;
    }

    function runWelcome() {
        var layer = document.querySelector('[data-companion-welcome]');
        if (!layer) return;
        var big = layer.querySelector('[data-welcome-avatar]');
        var docked = false;
        var timers = [];
        welcoming = true;

        function dock(instant) {
            if (docked) return;
            docked = true;
            timers.forEach(clearTimeout);
            removeSkipListeners();
            var from = big.getBoundingClientRect();
            var to = avatar.getBoundingClientRect();
            var finish = function () {
                layer.hidden = true;
                root.classList.remove('is-waiting');
                welcoming = false;
                setExpression('neutral');
            };
            if (instant || !from.width) {
                finish();
                return;
            }
            layer.classList.add('is-docking');
            big.style.transformOrigin = 'top left';
            big.style.transition = 'transform 600ms cubic-bezier(0.22, 0.8, 0.2, 1)';
            big.style.transform = 'translate(' + (to.left - from.left) + 'px,' + (to.top - from.top) + 'px) scale(' + (to.width / from.width) + ')';
            timers.push(setTimeout(finish, 620));
        }

        function skip() { dock(true); }
        function onKey(event) { if (event.key === 'Escape' || event.key === 'Enter' || event.key === ' ') skip(); }
        function removeSkipListeners() {
            window.removeEventListener('wheel', skip);
            window.removeEventListener('touchstart', skip);
            window.removeEventListener('keydown', onKey);
            layer.removeEventListener('click', skip);
        }

        window.addEventListener('wheel', skip, { passive: true });
        window.addEventListener('touchstart', skip, { passive: true });
        window.addEventListener('keydown', onKey);
        layer.addEventListener('click', skip);

        layer.hidden = false;
        root.classList.add('is-waiting');
        requestAnimationFrame(function () { layer.classList.add('is-visible'); });
        timers.push(setTimeout(function () { dock(false); }, 180 + 700));
    }

    if (eligibleForWelcome()) {
        var probe = new Image();
        var settled = false;
        var timeout = setTimeout(function () { settled = true; }, 1200);
        probe.onload = function () {
            clearTimeout(timeout);
            if (!settled) runWelcome();
        };
        probe.onerror = function () { clearTimeout(timeout); };
        probe.src = SHEET;
    }
})();
