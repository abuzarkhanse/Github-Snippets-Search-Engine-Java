async function search() {
    const query = document.getElementById('query').value;
    const language = document.getElementById('language').value;
    const resultsDiv = document.getElementById('results');
    const searchBtn = document.getElementById('searchBtn');

    if (!query || !language) {
        alert("Please enter a search query and select a language.");
        return;
    }

    // Change button to loading mode
    searchBtn.disabled = true;
    searchBtn.innerHTML = `
        <div class="button-spinner"></div> Loading...
    `;

    resultsDiv.innerHTML = '';

    try {
        const res = await fetch(`http://localhost:8080/api/snippets?query=${query}&language=${language}`);
        const data = await res.json();

        if (data.length === 0) {
            resultsDiv.innerHTML = "<p>No results found</p>";
        } else {
            data.forEach(snippet => {
                const div = document.createElement('div');
                div.className = 'snippet';
                div.innerHTML = `
                    <h3>${snippet.filename}</h3>
                    <pre>${snippet.code || "// Code not previewable"}</pre>
                    <a href="${snippet.repoUrl}" target="_blank">View on GitHub</a>
                `;
                resultsDiv.appendChild(div);
            });
        }
    } catch (error) {
        console.error("Error fetching data:", error);
        resultsDiv.innerHTML = "<p>Something went wrong. Please try again.</p>";
    }

    // Restore button to normal
    searchBtn.disabled = false;
    searchBtn.innerHTML = "Search";
}