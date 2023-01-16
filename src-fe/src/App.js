import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import LoginComponent from './components/auth/LoginComponent';
import RegistrationComponent from './components/auth/RegistrationComponent';
import HomePageComponent from './components/HomePageComponent';
import header from './components/Header';
import authStorage from './storage/AuthenticationTokenStorage'

const App = () => {

  return (
    <div className="App">
      {authStorage().getToken() !== null ? header() : null}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePageComponent />} />
          <Route path="/login" element={<LoginComponent />} />
          <Route path="/registration" element={<RegistrationComponent />} />
          <Route path="/server-errror" element={<div>There some server errors ocured</div>} />
        </Routes>
      </BrowserRouter>
    </div>
  );

}

export default App