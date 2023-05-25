import profilePicture from './photos/profilePicture.jpg';
import {FaGithub, FaLinkedin} from 'react-icons/fa';
import {Card} from "antd";

const gridStyle = {
    width: '25%',
    textAlign: 'center',
};

export function AboutMe() {
    return (
        <div className="about-me-container">
            <div className="profile-picture">
                <img src={profilePicture} alt="Profile"/>
            </div>
            <div className="skills-section">
                <Card title="Skills">
                    <Card.Grid style={gridStyle}>Content</Card.Grid>
                    <Card.Grid style={gridStyle}>Content</Card.Grid>
                    <Card.Grid style={gridStyle}>Content</Card.Grid>
                    <Card.Grid style={gridStyle}>Content</Card.Grid>
                    <Card.Grid style={gridStyle}>Content</Card.Grid>
                    <Card.Grid style={gridStyle}>Content</Card.Grid>
                </Card>
            </div>
            <div className="social-icons">
                <a href="https://github.com/JakubFir" target="_blank" rel="noopener noreferrer">
                    <FaGithub className="github-icon"/>
                </a>
                <a href="https://www.linkedin.com/in/jakub-firlejczyk-68b596273/" target="_blank"
                   rel="noopener noreferrer">
                    <FaLinkedin className="linkedin-icon"/>
                </a>
            </div>
        </div>
    );
}
