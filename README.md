# Socket Guardian

## Overview
**Socket Guardian** is an **Android application** designed to enhance **child safety** by controlling the **power outlets** in a room. Using an **ESP32 microcontroller** with **Bluetooth, Wi-Fi sockets, and sensors**, the system detects the presence of a child in a room and automatically **disables power outlets** to prevent accidents.

---

## Features
🔹 **Automated Power Control** – Disables all power sockets when a child enters the room.

🔹 **Real-time Monitoring** – Utilizes sensors connected to an ESP32 board to detect child presence.

🔹 **Wireless Communication** – Communicates seamlessly with the ESP32 board via Bluetooth and Wi-Fi.

🔹 **Cloud-Based Storage** – Uses Firebase to store and manage device and user data.

🔹 **User-Friendly Android Interface** – Intuitive UI for configuring and monitoring socket status.

---

## Technologies Used
### **Mobile Development**
- **Android (Java)** – Core application framework
- **Firebase Realtime Database** – Cloud-based data storage and synchronization

### **Embedded Systems**
- **ESP32 Board** – Microcontroller for handling Bluetooth, Wi-Fi, and sensor communication
- **Sensors** – Detect child presence in the room
- **Smart Sockets** – Controlled via ESP32 for power management

### **Networking & Communication**
- **Bluetooth** – Short-range communication between ESP32 and Android app
- **Wi-Fi** – Enables cloud connectivity and remote management

---

## System Architecture
```
+--------------------+        +------------+        +----------------+        +-------------+
|  Android App       | <----> |  Firebase  | <--->  | ESP32 Micro-   | <--->  | Sensors &   |
| (User Interface)   |        | (Cloud DB) |        | controller     |        | Smart Plugs |
+--------------------+        +------------+        +----------------+        +-------------+
```

---

## 📷 Screenshots
| Login Screen | Register Screen | Home Menu |
|--------------|-----------------|-----------|
| <img src="https://github.com/user-attachments/assets/e26c1e62-309d-4127-a22e-42417ca5d076" width="300" height="550"/> | <img src="https://github.com/user-attachments/assets/c8cac860-aad8-4bad-9cf2-ff4143f453cd" width="300" height="550"/> | <img src="https://github.com/user-attachments/assets/5a07150f-1b53-44bd-8dc4-689a3a49bca5" width="300" height="550"/>

| Device Menu | Power Socket | Settings |
|-------------|--------------|----------|
| <img src="https://github.com/user-attachments/assets/abe6e13c-9ba6-44cb-8355-1db2d88937d4" width="300" height="550"/> | <img src="https://github.com/user-attachments/assets/ab8dd4ec-b91b-42c3-917a-cfbd399147eb" width="300" height="550"/> | <img src="https://github.com/user-attachments/assets/df8515a3-281a-465f-bb27-1dc18ad2b52c" width="300" height="550"/>


---

## Installation & Setup
### **Prerequisites**
1. **Android Device** running Android 7.0+
2. **ESP32 Board** with Bluetooth & Wi-Fi capabilities
3. **Firebase Account** for real-time database access

### **Android App Setup**
1. Clone the repository:
   ```sh
   git clone https://github.com/vaggelisbarb/SocketGuardian.git
   cd SocketGuardian
   ```
2. Open in **Android Studio**.
3. Configure **Firebase**:
   - Create a Firebase project.
   - Download `google-services.json` and place it in `app/`.
4. Build and run the application on an **Android device** with Bluetooth enabled.

### **ESP32 Setup**
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

## How It Works
1. **Child Enters Room**: Sensors detect movement and presence.
2. **ESP32 Sends Signal**: Communicates with the Android app via Bluetooth/Wi-Fi.
3. **Android App Disables Outlets**: The app receives the signal and disables connected sockets.
4. **Alerts & Notifications**: Parents get notified of any status changes.
5. **Manual Override**: Users can re-enable sockets from the app if necessary.

---

## Future Enhancements
🔹 **AI-Based Child Detection** – Integrate machine learning for enhanced child presence detection.  
🔹 **Remote Monitoring & Alerts** – Implement notifications when power sockets are deactivated.  
🔹 **Multi-Room Support** – Expand functionality to manage multiple rooms simultaneously.

---

### **Development Team**  
- **Panagiotis Stavarakis** – *Co-Creator, Embedded Systems Developer & Database Contributor*  
- **Evangelos Barmpalias** – *Core Developer & Co-Creator*  
