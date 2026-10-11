// Web Audio Procedural Sound Engine for Vedic Math Quest
// 100% Offline, Zero external sound files, 0ms latency

(function(window) {
    let audioCtx = null;
    let isMuted = localStorage.getItem('vm_sound_enabled') === 'false';

    function getAudioContext() {
        if (!audioCtx) {
            const AudioContextClass = window.AudioContext || window.webkitAudioContext;
            if (AudioContextClass) {
                audioCtx = new AudioContextClass();
            }
        }
        if (audioCtx && audioCtx.state === 'suspended') {
            audioCtx.resume();
        }
        return audioCtx;
    }

    const soundEngine = {
        isMuted: function() {
            return isMuted;
        },
        toggleSound: function() {
            isMuted = !isMuted;
            localStorage.setItem('vm_sound_enabled', isMuted ? 'false' : 'true');
            return !isMuted;
        },
        playCorrect: function() {
            if (isMuted) return;
            const ctx = getAudioContext();
            if (!ctx) return;

            const now = ctx.currentTime;
            // High chime arpeggio: E6 (1318.5Hz) -> G#6 (1661.2Hz) -> B6 (1975.5Hz)
            [1318.5, 1661.2, 1975.5].forEach((freq, idx) => {
                const osc = ctx.createOscillator();
                const gain = ctx.createGain();
                osc.type = 'sine';
                osc.frequency.setValueAtTime(freq, now + idx * 0.06);

                gain.gain.setValueAtTime(0, now + idx * 0.06);
                gain.gain.linearRampToValueAtTime(0.18, now + idx * 0.06 + 0.015);
                gain.gain.exponentialRampToValueAtTime(0.001, now + idx * 0.06 + 0.25);

                osc.connect(gain);
                gain.connect(ctx.destination);

                osc.start(now + idx * 0.06);
                osc.stop(now + idx * 0.06 + 0.28);
            });
        },
        playWrong: function() {
            if (isMuted) return;
            const ctx = getAudioContext();
            if (!ctx) return;

            const now = ctx.currentTime;
            const osc = ctx.createOscillator();
            const gain = ctx.createGain();

            osc.type = 'sawtooth';
            osc.frequency.setValueAtTime(190, now);
            osc.frequency.exponentialRampToValueAtTime(95, now + 0.28);

            gain.gain.setValueAtTime(0.2, now);
            gain.gain.exponentialRampToValueAtTime(0.001, now + 0.3);

            osc.connect(gain);
            gain.connect(ctx.destination);

            osc.start(now);
            osc.stop(now + 0.32);
        },
        playTick: function() {
            if (isMuted) return;
            const ctx = getAudioContext();
            if (!ctx) return;

            const now = ctx.currentTime;
            const osc = ctx.createOscillator();
            const gain = ctx.createGain();

            osc.type = 'triangle';
            osc.frequency.setValueAtTime(800, now);
            osc.frequency.exponentialRampToValueAtTime(300, now + 0.04);

            gain.gain.setValueAtTime(0.08, now);
            gain.gain.exponentialRampToValueAtTime(0.001, now + 0.05);

            osc.connect(gain);
            gain.connect(ctx.destination);

            osc.start(now);
            osc.stop(now + 0.06);
        },
        playVictory: function() {
            if (isMuted) return;
            const ctx = getAudioContext();
            if (!ctx) return;

            const now = ctx.currentTime;
            const chord = [523.25, 659.25, 783.99, 1046.5]; // C5, E5, G5, C6
            chord.forEach((freq, idx) => {
                const osc = ctx.createOscillator();
                const gain = ctx.createGain();
                osc.type = 'triangle';
                osc.frequency.setValueAtTime(freq, now + idx * 0.09);

                gain.gain.setValueAtTime(0, now + idx * 0.09);
                gain.gain.linearRampToValueAtTime(0.22, now + idx * 0.09 + 0.02);
                gain.gain.exponentialRampToValueAtTime(0.001, now + idx * 0.09 + 0.55);

                osc.connect(gain);
                gain.connect(ctx.destination);

                osc.start(now + idx * 0.09);
                osc.stop(now + idx * 0.09 + 0.6);
            });
        }
    };

    window.soundEngine = soundEngine;

    // Unlock AudioContext on first touch
    ['click', 'touchstart'].forEach(evt => {
        window.addEventListener(evt, function unlock() {
            getAudioContext();
            window.removeEventListener(evt, unlock);
        }, { once: true, passive: true });
    });
})(window);
