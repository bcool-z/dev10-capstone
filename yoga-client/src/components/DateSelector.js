import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

function DateSelector({ selectedDate, handleDateChange }) {
    return (
      <div>
        <DatePicker
          selected={selectedDate}
          onChange={handleDateChange}
          dateFormat="MM/dd/yyyy"
        />
      </div>
    );
  }
  
  export default DateSelector;