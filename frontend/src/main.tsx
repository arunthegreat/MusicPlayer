import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App.tsx'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MusicPlayer from './MusicPlayer.tsx';
import AudioPlayer from './AudioPlayer/index.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/music/:id" element={<MusicPlayer/>} />
        <Route path="/play" element={<AudioPlayer/>} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
