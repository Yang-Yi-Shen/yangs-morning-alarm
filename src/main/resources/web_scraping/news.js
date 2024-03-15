import axios from "axios";
import * as cheerio from "cheerio";

// get top 3 news headlines of the day from Taiwan News
export default async function news() {
    const url = "https://www.taiwannews.com.tw/en/index";

    const response = await axios.get(url);
    const $ = cheerio.load(response.data);

    const headlines = [];

    // retrieve news headlines
    $("ul.test li a.de_remove-link-color").each((index, element) => {
        // stop once I have 3 headlines
        if (index < 3) {
            const headline = $(element);
            const title = headline.attr("title");

            headlines.push(title);
        }
    });

    return headlines;
}