import React from 'react';
import { useContext } from 'react';
import AuthContext from '../contexts/AuthContext';

function EnrollButton() {

  const { user } = useContext(AuthContext);

  return user ? (
    
   <button >Enroll</button>)
    : null;
  
}

export default EnrollButton;