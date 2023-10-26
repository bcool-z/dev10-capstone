import React from 'react';
import { useContext,useState,useEffect } from 'react';
import AuthContext from '../contexts/AuthContext';
import { makeReservation } from '../services/resService';
import { findUserByEmail } from '../services/userService';
import { useNavigate, useParams, Link } from "react-router-dom";

function EnrollButton({cls}) {

  const { user } = useContext(AuthContext);
  const [errors, setErrors] = useState([]);
  const navigate = useNavigate();

const [appUser,setAppUser]= useState("");
const reservation = {
  id: 0,
  session: cls,
  student: appUser
}

useEffect(() => {
  if (user) {
    findUserByEmail(user.username).then(setAppUser);
  }
}, [user]);

  const handleEnrollClick = (evt) => {
    evt.preventDefault();
    makeReservation(reservation).then((data) => {
      if (data?.errors) {
        setErrors(data.errors);
      } else {
        navigate("/", {
          state: { message: `${reservation.id} saved!` },
        });
      }
    });
  };

  return user ? (
    
   <button onClick={handleEnrollClick}>Enroll</button>)
    : null;
  
}

export default EnrollButton;