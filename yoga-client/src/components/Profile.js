import { useEffect, useState, useCallback, useContext } from "react";
import AuthContext from "../contexts/AuthContext";
import { findUserById } from "../services/userService";
import { findUserByEmail } from "../services/userService";
import { findResByUserId } from "../services/resService";
import { saveUser } from "../services/userService";
import { useNavigate } from "react-router-dom";
import ValidationSummary from "./ValidationSummary";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { formatDate, formattedTime, stringToDate } from "../services/dateUtils";
import { refreshToken } from "../services/authService";

export default function Profile() {
  const { user } = useContext(AuthContext);
  const [errors, setErrors] = useState([]);

  const [dispUser,setDispUser] = useState("");


  const [isEditing, setIsEditing] = useState(false);

  const userDob = new Date(user.dob); 
const [editedUser, setEditedUser] = useState({ ...dispUser, dob: stringToDate(dispUser.dob), emailAddress: dispUser.username, userType: dispUser.userType});

  const [reservations, setReservations] = useState([])
  const navigate = useNavigate();

  useEffect(() => {
    if (user) {
      console.log(user.appUserId);
      findResByUserId(user.appUserId).then(setReservations);
    }
  }, []);

  useEffect(() => {
    if (user) {
      console.log(user.appUserId);
      findUserById(user.appUserId).then((userData) => {
        console.log(userData);
        setDispUser(userData);
      });
    }
  }, []);

  const fetchAndUpdateUserData = (userId) => {
    findUserById(userId).then((userData) => {
      setDispUser(userData);
    });
  };


  const handleEditClick = () => {
    setIsEditing(true);
    setEditedUser({...dispUser, dob: stringToDate(dispUser.dob)});
    
  };

  const handleSaveClick = () => {
    //convert Date dob back to string
   setEditedUser({...editedUser, dob: formatDate(editedUser.dob)})
    saveUser(editedUser).then((data) => {
      if (data?.errors) {
        setErrors(data.errors);
      } else {
        fetchAndUpdateUserData(user.appUserId);
        navigate("/profile", {
          state: { message: `${editedUser.id} saved!` },
        });
      }
    });
 
    setIsEditing(false);
  };

  return (
    <>
      <ValidationSummary errors={errors} />
      <div>
        <h2>User Profile</h2>
        {isEditing ? (
          <div>
            <label>First Name:</label>
            <input
              type="text"
              value={editedUser.firstName}
              onChange={(e) =>
                setEditedUser({ ...editedUser, firstName: e.target.value })
              }
            />
            <br />
            <label>Last Name:</label>
            <input
              type="text"
              value={editedUser.lastName}
              onChange={(e) =>
                setEditedUser({ ...editedUser, lastName: e.target.value })
              }
            />
            <br />
            <label>Phone Number:</label>
            <input
              type="text"
              value={editedUser.phoneNumber}
              onChange={(e) =>
                setEditedUser({ ...editedUser, phoneNumber: e.target.value })
              }
            />
            <br />
            <label>Date of Birth:</label>
            <DatePicker
              selected={editedUser.dob}
              onChange={(date) => setEditedUser({ ...editedUser, dob: date })}
            />
            <br />
            <label>Email Address:</label>
            <input
              type="text"
              value={editedUser.username}
              onChange={(e) =>
                setEditedUser({ ...editedUser, emailAddress: e.target.value })
              }
            />
            <br />
       
            <button onClick={handleSaveClick}>Save</button>
          </div>
        ) : (
          <div>
            <p>First Name: {dispUser.firstName}</p>
            <p>Last Name: {dispUser.lastName}</p>
            <p>Phone Number: {dispUser.phoneNumber}</p>
            <p>Date of Birth: {dispUser.dob}</p>
            <p>Email: {dispUser.username}</p>
            
            <button onClick={handleEditClick}>Edit</button>
          </div>
        )}
      </div>

      <div>
        <h2>Classes Signed up for:</h2>

        {reservations.length === 0 ? (
          <p></p>
        ) : (
          <ul>
            {reservations.map((res) => (
              <li key={res.id}>{formattedTime(res.session.start)} - {formattedTime(res.session.end)}, {res.session.location.name} with {res.session.instructor.firstName} {res.session.instructor.lastName} </li>
            ))}
          </ul>
        )}
      </div>
    </>
  );
}
