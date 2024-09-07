import { createContext, useState, useEffect } from "react";
import { makeUserFromJwt } from "../services/authService";
// Create the AuthContext
const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
  
    useEffect(() => {
      const jwtToken = localStorage.getItem('jwt_token');
      if (jwtToken) {
        const userData = makeUserFromJwt(jwtToken);
        setUser(userData);
      }
    }, []);
  
    const logout = () => {
      localStorage.removeItem('jwt_token');
      setUser(null);
    };
  
    return (
      <AuthContext.Provider value={{ user, logout }}>
        {children}
      </AuthContext.Provider>
    );
  };

  export default AuthContext;