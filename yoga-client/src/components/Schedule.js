import React, {useState} from 'react';
import DateSelector from './DateSelector';
import ClassList from './ClassList';

export default function Schedule()
{
    const [selectedDate, setSelectedDate] = useState(new Date());
    // const [classes, setClasses] = useState([
    //   { id: 1, name: 'Yoga', date: new Date(2023, 9, 23) },
    //   { id: 2, name: 'Pilates', date: new Date(2023, 9, 23) },
    //   { id: 3, name: 'Zumba', date: new Date(2023, 9, 24) },
    // ]);
  
    const handleDateChange = (date) => {
      setSelectedDate(date);
    };
  
    return (
      <div>
        <h1>Class Sign-up</h1>
        <DateSelector selectedDate={selectedDate} handleDateChange={handleDateChange} />
        <ClassList selectedDate={selectedDate} />
      </div>
    );
  }
  