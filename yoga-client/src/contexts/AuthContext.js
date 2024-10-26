import { createContext, useState, useEffect } from "react";
import { makeUserFromJwt } from "../services/authService";
// Create the AuthContext
const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [appUser, setAppUser] = useState(null);
  
    useEffect(() => {
      const jwtToken = localStorage.getItem('jwt_token');
      if (jwtToken) {
        const appUserData = makeUserFromJwt(jwtToken);
        setAppUser(appUserData);
      }
    }, []);
  
    const logout = () => {
      localStorage.removeItem('jwt_token');
      setAppUser(null);
    };
  
    return (
      <AuthContext.Provider value={{ user: appUser, logout }}>
        {children}
      </AuthContext.Provider>
    );
  };

  export default AuthContext;