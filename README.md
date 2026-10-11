# ⚡ Vedic Math Quest: Mental Dojo

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" width="128" height="128" alt="Vedic Math Quest Icon" />
</p>

<p align="center">
  <b>Progression in game = Inevitable progression in mind</b><br>
  A hardcore mental math calculation dojo and cognitive speed arena packaged into a lightweight, high-performance Android APK.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android_14_%28API_34%29-3DDC84?logo=android&logoColor=white" alt="Platform" />
  <img src="https://img.shields.io/badge/Offline-100%25_Ready-brightgreen" alt="Offline Ready" />
  <img src="https://img.shields.io/badge/Cloud_Sync-Firestore_Realtime-FFA611?logo=firebase&logoColor=white" alt="Firestore Sync" />
  <img src="https://img.shields.io/badge/Build-GitHub_Actions-2088FF?logo=github-actions&logoColor=white" alt="GitHub Actions" />
</p>

---

## 🌟 Overview

**Vedic Math Quest** transforms Vedic mental calculation techniques into a high-stakes cognitive training ground. Designed for students, competitive exam candidates, and mental athletes looking to push their working memory and operational speed to superhuman levels.

---

## 🚀 Key Features

- 🔋 **100% Offline First**: Play anywhere, anytime without an internet connection. Styles (Tailwind), sound synthesizer, tutorials, and game logic load instantly with 0ms latency.
- 🔄 **Smart Auto-Sync**: Automatically reconciles with Firebase Firestore the moment network connection is established. All points, stars, streaks, and cleared stages are safely preserved.
- 🏋️ **Adaptive Training Dojo**:
  - Overclock Mode: Solve 5 consecutive questions in $\le 4.0\text{s}$ to qualify for official tournament stages.
  - Working Memory Carry Assist scratchpad.
  - Detailed diagnostic feedback for carry and product miscalculations.
- ⚔️ **Hardcore Tournament Arena**:
  - **World 1**: The 2×2 Realm (No-Carry, Single Carry, Heavy Memory, 2×2 Boss Deathmatch)
  - **World 2**: Speed Tactics Arsenal (Lightning 11s, Ending in 5 Squaring, Base 100 Nikhilam, Tactics Boss)
  - **World 3**: The 3×3 Realm (Light Digits, Grand Star Focus, Heavy Carries, 3×3 Boss)
  - **World 4**: Grandmaster Cup (4×4 Cross Multiplication & Mixed Championship)
- 🔊 **Procedural Web Audio Engine**: Zero external audio files; pure synthesized tones for hits, errors, countdown ticks, and victory fanfare.
- 📳 **Tactile Haptic Feedback**: Sharp native vibration pulses on correct and incorrect calculations.
- 📱 **Native Mobile Android Experience**:
  - Edge-to-edge dark obsidian design (`#09090b`).
  - Automatic numeric dialpad popup (`inputmode="numeric"`).
  - Android hardware back button navigation.
  - Safe area inset padding for modern camera cutouts and navigation bars.

---

## 📥 Download APK

Grab the latest signed release APK directly from GitHub Releases:
- 👉 [**Download Latest Release APK**](../../releases/latest)

---

## 🛠️ Architecture

- **Engine**: Native Android WebView with `androidx.webkit.WebViewAssetLoader`
- **Minimum SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **CI/CD**: GitHub Actions building signed release APK on push.
