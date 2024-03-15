import express from "express";

// import homemade functions
import news from "./news.js";

const app = express();
const PORT = 6900;

app.get("/api/news", async (req, res) => {
    // get the day's top 3 headlines (so far, anyway) from Taiwan News
    const headlines = await news();

    res.json(headlines);
})

app.listen(PORT, () => {
    console.log("server successfully started");
})