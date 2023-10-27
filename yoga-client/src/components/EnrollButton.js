import React from 'react';
import { useContext,useState,useEffect } from 'react';
import AuthContext from '../contexts/AuthContext';
import { makeReservation } from '../services/resService';
import { findUserByEmail } from '../services/userService';
import { useNavigate } from "react-router-dom";
import ValidationSummary from "./ValidationSummary";

function EnrollButton({cls,onError}) {

  const { user } = useContext(AuthContext);
  const [errors, setErrors] = useState([]);
  const navigate = useNavigate();

const [reservation, setReservation] = useState( {
  id: 0,
  session: cls,
  student: user
})

  const handleEnrollClick = (evt) => {
    evt.preventDefault();
    makeReservation(reservation).then((data) => {
      if (data?.errors) {
        setErrors(data.errors);
        onError(data.errors);
      } else {
        navigate("/", {
          state: { message: `${reservation.id} saved!` },
        });
      }
    })
    .catch((error) => {
      // Handle the error that occurred during the reservation process.
      console.error("An error occurred:", error);
      // You can also set an error state or perform other error handling as needed.
    });
  };

  return user ? (
    
   <button onClick={handleEnrollClick}>Enroll</button>)
    : null;
  
}

export default EnrollButton;