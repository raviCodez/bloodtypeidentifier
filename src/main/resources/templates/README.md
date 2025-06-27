# Blood Type Identifier Frontend

This is a React frontend for the Blood Type Identifier project, created with Vite.

## Features
- Upload an image of a blood test result
- Submit the image to the backend `/identify` endpoint
- Display the predicted blood type and the uploaded image/result

## Getting Started

1. Install dependencies:
   ```sh
   npm install
   ```
2. Start the development server:
   ```sh
   npm run dev
   ```
3. The app will be available at `http://localhost:5173` by default.

## Backend API
- The backend should expose a POST `/identify` endpoint that accepts an image file and returns the blood type prediction and image path.

## Customization
- Update the API endpoint in the frontend code if your backend runs on a different host or port.
