# Socket Guardian

## 📌 Overview
**Socket Guardian** is an **Android application** designed to enhance **child safety** by controlling the **power outlets** in a room. Using an **ESP32 microcontroller** with **Bluetooth, Wi-Fi sockets, and sensors**, the system detects the presence of a child in a room and automatically **disables power outlets** to prevent accidents.

---

## 🚀 Features
- 🔌 **Automated Power Control**: Disables power outlets when a child is detected.
- 📡 **ESP32 Integration**: Communicates with sensors to monitor room activity.
- 📲 **Android Application**: Provides real-time monitoring and manual override options.
- 🔥 **Firebase Database**: Stores user data, device configurations, and logs.
- 📶 **Bluetooth & Wi-Fi Connectivity**: Enables seamless communication between the Android app and ESP32.
- 🔔 **Notifications**: Alerts parents when a child enters or leaves the room.

---

## 🛠️ Technologies Used
### **📱 Android Application**
- **Java** for mobile development
- **Firebase Firestore** for real-time data storage
- **Bluetooth API** for ESP32 communication
- **Retrofit** for network requests (if needed)

### **🖥️ Embedded System (ESP32)**
- **Arduino IDE** for firmware development
- **Bluetooth & Wi-Fi modules** for communication
- **Motion & Presence Sensors** for child detection
- **Relay Modules** for controlling power sockets

---

## 📷 Screenshots
| Login Screen | Register Screen | Home Menu |
|--------------|-----------------|-----------|
| <img src="https://github.com/user-attachments/assets/e26c1e62-309d-4127-a22e-42417ca5d076" width="300" height="600"/> | <img src="https://github.com/user-attachments/assets/c8cac860-aad8-4bad-9cf2-ff4143f453cd" width="300" height="600"/> | <img src="https://github.com/user-attachments/assets/5a07150f-1b53-44bd-8dc4-689a3a49bca5" width="300" height="600"/>



---

## 🔧 Setup & Installation
### **📱 Android App Setup**
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/SocketGuardian.git
   cd SocketGuardian
   ```
2. Open in **Android Studio**.
3. Configure **Firebase**:
   - Create a Firebase project.
   - Download `google-services.json` and place it in `app/`.
4. Build and run the application on an **Android device** with Bluetooth enabled.

### **🔌 ESP32 Setup**
1. Install **Arduino IDE** or **ESP-IDF**.
2. Add ESP32 Board Support:
   - Install ESP32 libraries.
   - Select ESP32 as the target board.
3. Upload the firmware to ESP32:
   ```sh
   esptool.py --port /dev/ttyUSB0 write_flash 0x10000 firmware.bin
   ```
4. Place the ESP32 with sensors in the room and power it on.

---

## 📡 How It Works
1. **Child Enters Room**: Sensors detect movement and presence.
2. **ESP32 Sends Signal**: Communicates with the Android app via Bluetooth/Wi-Fi.
3. **Android App Disables Outlets**: The app receives the signal and disables connected sockets.
4. **Alerts & Notifications**: Parents get notified of any status changes.
5. **Manual Override**: Users can re-enable sockets from the app if necessary.

---

## 🎯 Future Improvements
- 🔄 **Machine Learning**: Improve child detection accuracy.
- 🌐 **Remote Monitoring**: Control sockets via the internet.
- 📊 **Usage Analytics**: Track power usage for insights.

---

## 🤝 Contributing
Contributions are welcome! Feel free to submit a pull request or report an issue.
