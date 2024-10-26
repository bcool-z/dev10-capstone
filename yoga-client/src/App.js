import { useEffect, useState, useCallback } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate, Navigate } from "react-router-dom";
import './App.css';
import AuthContext from "./contexts/AuthContext";
import NavBar from "./components/NavBar";
import Home from "./components/Home";
import Error from "./components/Error";
import LoginForm from "./components/LoginForm";
import SignUpForm from "./components/SignUpForm";
import About from "./components/About";
import Manage from "./components/Manage";
import ClassForm from "./components/ClassForm";

import { refreshToken, logout } from "./services/authService";
import Schedule from "./components/Schedule";
import Profile from "./components/Profile";

import 'bootstrap/dist/css/bootstrap.min.css';

const TIMEOUT_MILLISECONDS = 14 * 60 * 1000;

function App() {
  const [appUser, setAppUser] = useState();
  const [initialized, setInitialized] = useState(false);

  const resetAppUser = useCallback(() => {
    refreshToken()
      .then((appUser) => {
        setAppUser(appUser);
        setTimeout(resetAppUser, TIMEOUT_MILLISECONDS);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => setInitialized(true));
  }, []);

  useEffect(() => {
    resetAppUser();
  }, [resetAppUser]);


  const auth = {
    appUser: appUser,
    handleLoggedIn(appUser) {
      setAppUser(appUser);
      setTimeout(resetAppUser, TIMEOUT_MILLISECONDS);
    },
    hasAuthority(authority) {
      return appUser?.authorities.includes(authority);
    },
    logout() {
      logout();
      setAppUser(null);
    },
  };

  if (!initialized) {
    return null;
  }

  const renderWithAuthority = (Component, ...authorities) => {
    for (let authority of authorities) {
      if (auth.hasAuthority(authority)) {
        return <Component />;
      }
    }
    return <Error />;
  };


  return (
    <div className="container">
      <AuthContext.Provider value={auth}>
      <Router>
        <NavBar>

        </NavBar>
        <Routes>

          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />}/>
          <Route path="/manage" element={<Manage />}/>
          <Route path="/login" element={<LoginForm />} />
          <Route path="/signup" element={<SignUpForm />} />
          <Route path="/schedule" element={<Schedule />} />
          <Route path="/profile/:profileId" element={<Profile />}/>
          <Route path="/ClassForm" element={<ClassForm />} />
          
        </Routes>
        </Router>

      </AuthContext.Provider>
     

    </div>
  );
}

export default App;
