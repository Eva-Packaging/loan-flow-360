import React, { useEffect } from 'react'
import apiClient from '../api/apiClient'

const Home = () => {
    useEffect(() =>{
        const fetchPosts = async () => {
            try{
                const response = await apiClient.get('/posts');
                console.log("Posts:", response.data);
            }catch (error) {
                console.error("Error fetching posts:", error);
            }
        } 

        fetchPosts();

    }, [])
  return (
    <div>This is the Home Page</div>
  )
}

export default Home