import { useEffect, useState } from "react";
import api from "../api/axios";
import Layout from "../components/Layout";

function Profile() {

    const [profile, setProfile] = useState({
        name: "",
        email: ""
    });

    useEffect(() => {
        fetchProfile();
    }, []);

    const fetchProfile = async () => {

        try {

            const res = await api.get("/profile");

            setProfile(res.data);

        } catch (err) {

            console.error(err);

        }

    };

    const handleChange = (e) => {

        setProfile({
            ...profile,
            [e.target.name]: e.target.value
        });

    };

    const updateProfile = async (e) => {

        e.preventDefault();

        try {

            await api.put("/profile", {
                name: profile.name
            });

            alert("Profile Updated Successfully");

        } catch (err) {

            console.error(err);

            alert("Update Failed");

        }

    };

    return (

        <Layout>

            <h1>My Profile</h1>

            <form onSubmit={updateProfile}>

                <div>

                    <label>Name</label>

                    <br />

                    <input
                        type="text"
                        name="name"
                        value={profile.name}
                        onChange={handleChange}
                    />

                </div>

                <br />

                <div>

                    <label>Email</label>

                    <br />

                    <input
                        type="email"
                        value={profile.email}
                        disabled
                    />

                </div>

                <br />

                <button type="submit">
                    Update Profile
                </button>

            </form>

        </Layout>

    );

}

export default Profile;