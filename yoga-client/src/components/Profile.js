import { useEffect, useState, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { findUserById, saveUser } from "../services/userService";
import { findResByUserId } from "../services/resService";
import ValidationSummary from "./ValidationSummary";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { formatDate, stringToDate } from "../services/dateUtils";

export default function Profile() {
  const { profileId } = useParams(); // Get userId from URL parameters
  const { appUser } = useContext(AuthContext); // Get the current authenticated user
  const [errors, setErrors] = useState([]);
  const [dispUser, setDispUser] = useState(null); // To hold the profile being displayed
  const [isEditing, setIsEditing] = useState(false);
  const [editedUser, setEditedUser] = useState(null);
  const [reservations, setReservations] = useState([]);
  const navigate = useNavigate();

  // Determine if the profile being viewed is the current user's
  const isCurrentUser = appUser && appUser.id === parseInt(profileId);
  const isInstructor = appUser?.authorities?.includes("INSTRUCTOR");
  // Fetch user and reservations by userId (from the URL)
  useEffect(() => {
    if (profileId) {
      findUserById(profileId).then((userData) => {
        console.log("User data:", userData);
        setDispUser(userData);
        setEditedUser(userData);
      });

      findResByUserId(profileId).then(setReservations);
    }
  }, [profileId]);

  // Handle edit/save logic
  const handleEditClick = () => {
    setIsEditing(true);
    setEditedUser({ ...dispUser, dob: stringToDate(dispUser.dob) });
  };

  const handleSaveClick = () => {

    
    const formattedDate = formatDate(editedUser.dob);
    console.log("about to be saved:", {...editedUser, dob: formattedDate});

    saveUser({...editedUser, dob: formattedDate}).then((data) => {
      if (data?.errors) {
        setErrors(data.errors);
      } else {
        setIsEditing(false);
        setDispUser({...editedUser, dob:formattedDate}); // Update display with saved data
        navigate(`/profile/${profileId}`, { state: { message: `${editedUser.id} saved!` } });
      }
    });
  };

  if (!dispUser) return <div>Loading...</div>;

  return (
    <>
      <ValidationSummary errors={errors} />
      <div>
        <h2>{isCurrentUser ? "Your Profile" : `${dispUser.firstName}'s Profile`}</h2>
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
              selected={editedUser.dob ? new Date(editedUser.dob) : null}
              onChange={(date) =>
                setEditedUser({...editedUser, dob: date})
              }
            />

            <br />
            <label>Email Address:</label>
            <input
              type="text"
              value={editedUser.emailAddress}
              onChange={(e) =>
                setEditedUser({ ...editedUser, emailAddress: e.target.value })
              }
            />
            <br />
            <label>Instructor?</label>
            <input 
            type= "checkbox"
            checked={editedUser.userType==="INSTRUCTOR"}
            onChange={(e)=>{
              const isChecked = e.target.checked;
              const updatedUserType = isChecked
              ? editedUser.userType = "INSTRUCTOR" : editedUser.userType = "STUDENT";
              setEditedUser({...editedUser, userType: updatedUserType})
            }} />
            <br/>
            <button onClick={handleSaveClick}>Save</button>
          </div>
        ) : (
          <div>
            <p>First Name: {dispUser.firstName}</p>
            <p>Last Name: {dispUser.lastName}</p>
            <p>Phone Number: {dispUser.phoneNumber}</p>
            <p>Date of Birth: {dispUser.dob}</p>
            <p>Email: {dispUser.emailAddress}</p>
            {isInstructor&&(<p>{dispUser.userType === "INSTRUCTOR" ? "Instructor ✔️" : ""}</p>)}
            {(isCurrentUser || isInstructor) && <button onClick={handleEditClick}>Edit</button>}
          </div>
        )}
      </div>

      <div>
        <h2>Classes Signed up for:</h2>
        {reservations.length === 0 ? (
          <p>No reservations found.</p>
        ) : (
          <ul>
            {reservations.map((res) => (
              <li key={res.id}>
                {res.session.start} - {res.session.end}, {res.session.location.name} with {res.session.instructor.firstName} {res.session.instructor.lastName}
              </li>
            ))}
          </ul>
        )}
      </div>
    </>
  );
}
