import { useEffect, useState, useCallback } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate, Navigate } from "react-router-dom";
import './App.css';
import AuthContext from "./contexts/AuthContext";
import NavBar from "./components/NavBar";
import Home from "./components/Home";
import Error from "./components/Error";
import LoginForm from "./components/LoginForm";
import SignUpForm from "./components/SignUpForm"

import { refreshToken, logout } from "./services/authAPI";
import Schedule from "./components/Schedule";
import Profile from "./components/Profile";

const TIMEOUT_MILLISECONDS = 14 * 60 * 1000;

function App() {
  const [user, setUser] = useState();
  const [initialized, setInitialized] = useState(false);

  const resetUser = useCallback(() => {
    refreshToken()
      .then((user) => {
        setUser(user);
        setTimeout(resetUser, TIMEOUT_MILLISECONDS);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => setInitialized(true));
  }, []);

  useEffect(() => {
    resetUser();
  }, [resetUser]);


  const auth = {
    user: user,
    handleLoggedIn(user) {
      setUser(user);
      setTimeout(resetUser, TIMEOUT_MILLISECONDS);
    },
    hasAuthority(authority) {
      return user?.authorities.includes(authority);
    },
    logout() {
      logout();
      setUser(null);
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
          <Route path="/login" element={<LoginForm />} />
          <Route path="/signup" element={<SignUpForm />} />
          <Route path="/schedule" element={<Schedule />} />
          <Route path="/profile" element={<Profile />}/>
        </Routes>
        </Router>

      </AuthContext.Provider>
     

    </div>
  );
}

export default App;
