import { useState, useEffect } from "react";
import { searchUsers,saveUser } from '../services/userService'; // Import your search function
import { Modal, Button, ListGroupItem } from 'react-bootstrap'; 
import { useNavigate } from "react-router-dom";
import UserSearch from "./UserSearch";
import { fetchLocations,saveLocation } from "../services/locationService";




function Manage() {

  const navigate = useNavigate();


  const handleRowDoubleClick = (user) => {
    console.log(user.id)
    navigate(`/profile/${user.appUserId}`, { state: { user } });
  };

const [locations,setLocations] = useState([]);
const [locationEditor,showLocationEditor] = useState(false);
const [editedLocation,setEditedLocation] = useState();

const handleLocationSave=()=>{
saveLocation(editedLocation);
showLocationEditor(false);
setEditedLocation(null);
}

const handleLocationCreate=()=>{
  setEditedLocation({...editedLocation, id:0, name:'', size:'', description:''})
  showLocationEditor(true);
}

const handleLocationEdit =(location)=>{
setEditedLocation(location);
showLocationEditor(true);  
}
  useEffect(()=>{
    fetchLocations().then(setLocations);
  },[])

  return (
    <div className="manage-userss">
      <h1>Manage Users</h1>
      <UserSearch onUserSelect={handleRowDoubleClick}/>


      <h1>Locations</h1> 
      <button type="button" className="btn btn-primary" onClick={handleLocationCreate}>Add New</button>
      <table className="table table-bordered">
      <thead>
        <tr>
          <th>Name</th>
          <th>Size</th>
          <th>Description</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
      {locations.map((location)=>(
      <tr key={location.id}>
        <td>{location.name}</td>
        <td>{location.size}</td>
        <td>{location.description}</td>
        <td style={{display:'flex'}}>
        <button type="button" className="btn btn-secondary" onClick={()=>handleLocationEdit(location)}>Edit</button>
        <button type="button" className="btn btn-danger">Delete</button>
        </td>
      </tr>
          ))}
      </tbody>
      </table>

      {locationEditor && (
        <div>
          <h2>{editedLocation.id > 0 ? "Edit Location" : "Add Location"}</h2>
        <label>Name</label>
        <input type="text"
        value={editedLocation.name}
        onChange={(e)=>setEditedLocation({...editedLocation, name:e.target.value})} 
        />
        <br/>
        <label>Size</label>
        <select value={editedLocation.size}
        onChange={(e)=>setEditedLocation({...editedLocation, size:e.target.value})}>
          <option value="SMALL">Small</option>
          <option value="MEDIUM">Medium</option>
          <option value="LARGE">Large</option>
        </select>
        <br/>
        <label>Address</label>
        <textarea cols="30" rows="10"
        value={editedLocation.description}
        onChange={(e)=>setEditedLocation({...editedLocation, description:e.target.value})}>
        </textarea>
        <br/>
        <button className="btn btn-primary" onClick={handleLocationSave}>Save</button>
        <button className="btn btn-secondary" onClick={()=>showLocationEditor(false)}>Cancel</button>
        </div>
        
      )}
     
    </div>
  );
}

export default Manage;


