import React, { useState, useContext, useEffect } from 'react';
import DateSelector from './DateSelector';
import ClassList from './ClassList';
import ValidationSummary from "./ValidationSummary";
import AuthContext from '../contexts/AuthContext';
import { Button } from 'react-bootstrap'; 
import { useNavigate, useLocation } from 'react-router-dom';
import { formatDate } from '../services/dateUtils';

export default function Schedule() {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [errors, setErrors] = useState([]);
  const [successMessage, setSuccessMessage] = useState();

  const { appUser } = useContext(AuthContext);
  
  const navigate = useNavigate();
  const location = useLocation();

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const handleClassAdd = () => {
   
   navigate('/ClassForm',{ state: { sessionId: 0 } });
    
  };

  const isInstructor = appUser?.authorities?.includes("INSTRUCTOR");

  useEffect(()=>{
    if(location.state?.successMessage){
      setSuccessMessage(location.state.successMessage)
    }
  })

  return (
    <div>
      {successMessage && <div className="alert alert-success">{successMessage}</div>}
      <h1>Class Sign-up</h1>
      <DateSelector selectedDate={selectedDate} handleDateChange={handleDateChange} />
      <ClassList selectedDate={selectedDate} />

      {/* Button only visible to instructors */}
      {isInstructor && (
        <>
          <Button variant="primary" onClick={handleClassAdd}>New Class</Button>

          
        </>
      )}
    </div>
  );
}
