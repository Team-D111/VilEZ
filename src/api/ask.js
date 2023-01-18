import { jsonInstance, formdataInstance } from "./instance";

const jsonAxios = jsonInstance(); //eslint-disable-line no-unused-vars
const formdataAxios = formdataInstance();

// GET

async function getAskArticleList() {
  try {
    const { data } = await jsonAxios.get(`/askboard`);

    return data;
  } catch (error) {
    console.log(error);
  }
}

// POST

async function postAskArticle(formData) {
  try {
    const { data } = await formdataAxios.post(`/askboard`, formData);

    data.flag ? alert("등록되었습니다 😀") : alert("등록에 실패하였습니다 😥");
  } catch (error) {
    console.log(error);
  }
}

export { getAskArticleList, postAskArticle };
