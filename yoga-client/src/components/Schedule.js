import React, { useState, useContext } from 'react';
import DateSelector from './DateSelector';
import ClassList from './ClassList';
import ValidationSummary from "./ValidationSummary";
import AuthContext from '../contexts/AuthContext';
import { Button } from 'react-bootstrap'; 
import { useNavigate } from 'react-router-dom';

export default function Schedule() {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [errors, setErrors] = useState([]);

  const { appUser } = useContext(AuthContext);
  const navigate = useNavigate();
  // Handler for opening the modal


  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const handleClassAdd = () => {
   
   navigate('/ClassForm',{ state: { session: { id:0 } } });
    
  };

  const isInstructor = appUser?.authorities?.includes("INSTRUCTOR");

  return (
    <div>
      <ValidationSummary errors = {errors} />
      <h1>Class Sign-up</h1>
      {/* <DateSelector selectedDate={selectedDate} handleDateChange={handleDateChange} />
      <ClassList selectedDate={selectedDate} /> */}

      {/* Button only visible to instructors */}
      {isInstructor && (
        <>
          <Button variant="primary" onClick={handleClassAdd}>New Class</Button>

          
        </>
      )}
    </div>
  );
}
