const axios = require('axios')
const fs = require('fs')
const path = require('path')

const api_gateway_path = "http://localhost:8080";

const microserviceList = [
  { name: "auth-service", path: "/auth/v3/api-docs" },
  { name: "user-service", path: "/users/v3/api-docs" },
  { name: "data-service", path: "/data/v3/api-docs" },
  { name: "masterdata-service", path: "/masterdata/v3/api-docs" },
  { name: "analytics-service", path: "/analytics/v3/api-docs" },
];

const fetchApiDocs = async () => {
  for (const service of microserviceList) {
    const url = api_gateway_path + service.path;

    try {
      const response = await axios.get(url);
      console.log(__dirname);

      const outputpath = path.join(__dirname, `./${service.name}.json`);

      fs.writeFileSync(outputpath, JSON.stringify(response.data, null, 2));
      console.log(`fetched API from ${service.name}  successfully.`);
    } catch (error) {
      console.log(
        `Error while fetching api docs from ${service.name}, update fetch-api-docs to get full error`
      );

      // console.error(error);
    }
  }
};

fetchApiDocs();
