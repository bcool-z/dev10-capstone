import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchLocations } from '../services/locationService'; // Assumes a function to fetch locations
import UserSearch from './UserSearch';
import { getSessionById, saveSession } from '../services/sessionService';
import { useNavigate,useLocation } from 'react-router-dom';
import ValidationSummary from './ValidationSummary';

export default function ClassForm() {
    const location = useLocation();
    const sessionId = location.state?.sessionId;

    useEffect(()=>{
       if(sessionId){
            getSessionById(sessionId).then((sessionData)=>{
                setSession(sessionData);
            }
            )

       }
       else { setSession({...session,id:0,start:'',end:'',capacity:'',instructorId:null,locationId:null})}
    },[sessionId])

    const [session, setSession] = useState();
    const [locations, setLocations] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [errors,setErrors] = useState([]);
    const handleShowModal = () => {
        setShowModal(true);
    }

    const handleCloseModal = () => {
        setShowModal(false);
    }

    const handleDoubleClick = () => {

    }

    useEffect(() => {
        // Fetch available locations on load
        fetchLocations().then(setLocations);
    }, []);

    const navigate = useNavigate();

    const handleSave = async () => {
       const result = await saveSession(session);
       if(result?.errors && result.errors.length>0){
            setErrors(result.errors);
       }
       else{
        navigate("/schedule", {state: {successMessage:"Class Created"}});}
    }


    return (
        <div>
            <ValidationSummary errors = {errors}/>
            {session ? (
                <div>
            <h2>{session.id === 0 ? 'New Class' : 'Edit Class'}</h2>
            <Form>
                <Form.Group>
                    <Form.Label>Start Date & Time</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        value={session.start}
                        onChange={(e) => setSession({ ...session, start: e.target.value })}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>End Date & Time</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        value={session.end}
                        onChange={(e) => setSession({ ...session, end: e.target.value })}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Capacity</Form.Label>
                    <Form.Control
                        type="number"
                        value={session.capacity}
                        onChange={(e) => setSession({ ...session, capacity: e.target.value })}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Location</Form.Label>
                    <Form.Control
                        as="select"
                        value={session.location?.id || ''}
                        onChange={(e) => setSession({ ...session, location: locations.find(loc => loc.id === parseInt(e.target.value)) })}
                    >
                        <option value="">Select a location</option>
                        {locations.map(loc => (
                            <option key={loc.id} value={loc.id}>{loc.name}</option>
                        ))}
                    </Form.Control>
                </Form.Group>

                <Form.Group>
                    <Form.Label>Instructor</Form.Label>
                    <br/>
                    <Button onClick={handleShowModal}>Search</Button>
                    <span style = {{marginLeft: '10px'}}>
                        {session.instructor ?
                        `${session.instructor.firstName} ${session.instructor.lastName}` 
                        : "no instructor selected"}
                    </span>
                </Form.Group>
                    <br/>
                <Button variant="primary" onClick={handleSave}>Save</Button>
            </Form> 
            </div>):(
                <h2>Loading...</h2>
            )}


            {/* Instructor Selection Modal */}
            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    Find Instructor
                    </Modal.Header>
          <UserSearch onUserSelect={(user)=>{setSession({...session, instructor: user});
        handleCloseModal();}}/>
            </Modal>
        </div>
    );
}