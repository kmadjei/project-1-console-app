
window.onload = function () 
{
    // monitor form submission
    document.getElementById("loginForm").addEventListener("submit", handleSubmit);

    
    async function handleSubmit(event) {
        
        // prevent form submission
        event.preventDefault();

        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();

        //const await kk = f
        

    }

    async function handleValidation(email, password) {

        const url = "http://localhost:1234/log"
    }

}





