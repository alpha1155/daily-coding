const axios = require('axios');

const url = 'https://qa-st2.sap.entlms.cloud.sap/dest/srv/rest/admin/EntitlementRepository/entitlement/list';
const headers = {
  'accept': 'application/json',
  'accept-language': 'en',
  'content-type': 'application/json; charset=UTF-8',
  'sec-ch-ua': '"Chromium";v="134", "Not:A-Brand";v="24", "Google Chrome";v="134"',
  'sec-ch-ua-mobile': '?0',
  'sec-ch-ua-platform': '"Windows"',
  'sec-fetch-dest': 'empty',
  'sec-fetch-mode': 'cors',
  'sec-fetch-site': 'same-origin',
  'x-csrf-token': '3c737479b53cd238-UuV89eU54Ve1SGBeDL5TsMPb4b4',
  'x-requested-with': 'XMLHttpRequest',
  'cookie': 'country=CN; s_plt=4.30; s_pltp=undefined; AMCVS_227AC2D754DCAB340A4C98C6%40AdobeOrg=1; AMCV_227AC2D754DCAB340A4C98C6%40AdobeOrg=179643557%7CMCIDTS%7C20232%7CMCMID%7C57834236975007810841527272681771185211%7CMCAAMLH-1748586923%7C3%7CMCAAMB-1748586923%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1747989324s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C5.5.0; dtCookie=v_4_srv_9_sn_75CB0BF0D77A5754BB52F2A66F80FB3C_perc_100000_ol_0_mul_1_app-3Acab67a9e1543df82_0; s_cc=true; s_sq=sap-blackhole%3D%2526pid%253Dbtpcockpit-cockpit%25253A%2526pidt%253D1%2526oid%253DClose%2526oidt%253D3%2526ot%253DSUBMIT%26sapsapbtpcockpitprod%3D%2526pid%253Dbtpcockpit-cockpit%25253A%2526pidt%253D1%2526oid%253Dfunction%252528%252529%25257Btruste.bn.msglog%252528%252522accepts%252522%25252Ce.bannerMsgURL%252529%25252Ce.feat.iabBannerApplies%25253Fv%252528%252522process_iab_accept_%2526oidt%253D2%2526ot%253DSUBMIT; __VCAP_ID__=442dc047-89c9-445d-7b85-6c69; JSESSIONID=s%3ASMU1bs9RPgE03SwUuylXRO4te8mMSHlh.iiZfv4ww%2FNMlZAQbusn5qETAw3ZOn1jAtnen%2BftQF3Q',
  'Referer': 'https://qa-st2.sap.entlms.cloud.sap/cp.portal/site?sap-language=en',
  'Referrer-Policy': 'strict-origin-when-cross-origin'
};

const data = {
  "acquireColumns": [
    "entitlementNo",
    "customerNameSnapShot",
    "commercialProductNameSnapShot",
    "entitlementModelNameSnapShot",
    "quantity",
    "status",
    "validFrom",
    "validTo",
    "lastChangedOn",
    "tEx4",
    "bEx5",
    "bEx7",
    "sEx9",
    "nEx11",
    "sEx7",
    "businessCategoryName",
    "nEx18",
    "createdOn",
    "createdBy",
    "Customer.tEx3",
    "Customer.nEx8",
    "Customer.bEx1",
    "Customer.tEx2",
    "Customer.nEx2",
    "Customer.nEx4",
    "Customer.nEx5",
    "Customer.nEx3",
    "Customer.sEx1",
    "Customer.sEx2",
    "Customer.sEx3",
    "Customer.sEx4",
    "Customer.sEx5",
    "Customer.sEx6",
    "Customer.sEx7",
    "Customer.sEx8",
    "Customer.sEx9",
    "Customer.sEx10",
    "Customer.nEx6",
    "Customer.deleted",
    "Customer.groupCode",
    "Customer.nEx1",
    "Customer.validTo",
    "tEx3",
    "distributionChannelName",
    "sEx3",
    "nEx5",
    "sEx2",
    "tEx1",
    "bEx1",
    "bEx2",
    "nEx4",
    "nEx3",
    "sEx4",
    "nEx2",
    "entitlementTypeName",
    "folderName",
    "geoLocation",
    "lastChangedBy",
    "leadingEntitlementNo",
    "legacyTrancDocNo",
    "bEx6",
    "sEx8",
    "nEx14",
    "nEx15",
    "Offering.categoryCode",
    "Offering.deleted",
    "Offering.nEx7",
    "Offering.nEx1",
    "Offering.bEx1",
    "Offering.tEx1",
    "Offering.tEx2",
    "Offering.tEx3",
    "Offering.tEx4",
    "Offering.tEx5",
    "Offering.nEx4",
    "Offering.nEx2",
    "Offering.nEx3",
    "Offering.nEx5",
    "Offering.nEx6",
    "Offering.sEx1",
    "Offering.sEx2",
    "originationDocNo",
    "nEx13",
    "refDocNo",
    "remainingQuantity",
    "distributorName",
    "Distributor.tEx3",
    "Distributor.nEx8",
    "Distributor.bEx1",
    "Distributor.tEx2",
    "Distributor.nEx2",
    "Distributor.nEx4",
    "Distributor.nEx5",
    "Distributor.nEx3",
    "Distributor.sEx1",
    "Distributor.sEx2",
    "Distributor.sEx3",
    "Distributor.sEx4",
    "Distributor.sEx5",
    "Distributor.sEx6",
    "Distributor.sEx7",
    "Distributor.sEx8",
    "Distributor.sEx9",
    "Distributor.sEx10",
    "Distributor.nEx6",
    "Distributor.deleted",
    "Distributor.groupCode",
    "Distributor.nEx1",
    "Distributor.validTo",
    "sourceSystem",
    "theRightName",
    "thirdPartyName",
    "ThirdParty.tEx3",
    "ThirdParty.nEx8",
    "ThirdParty.bEx1",
    "ThirdParty.tEx2",
    "ThirdParty.nEx2",
    "ThirdParty.nEx4",
    "ThirdParty.nEx5",
    "ThirdParty.nEx3",
    "ThirdParty.sEx1",
    "ThirdParty.sEx2",
    "ThirdParty.sEx3",
    "ThirdParty.sEx4",
    "ThirdParty.sEx5",
    "ThirdParty.sEx6",
    "ThirdParty.sEx7",
    "ThirdParty.sEx8",
    "ThirdParty.sEx9",
    "ThirdParty.sEx10",
    "ThirdParty.nEx6",
    "ThirdParty.deleted",
    "ThirdParty.groupCode",
    "ThirdParty.nEx1",
    "ThirdParty.validTo",
    "emsTimeZone",
    "nEx10",
    "uom",
    "guid",
    "customerId",
    "statusCode",
    "folderCode",
    "commercialProductId",
    "distributorId",
    "thirdPartyId",
    "entitlementTypeCode",
    "entitlementModelId",
    "customerSystem",
    "offeringSystem",
    "phaseRelevant",
    "rootEntitlementGuid"
  ],
  "filters": [],
  "paging": {
    "pageNum": 1,
    "pageSize": 99999
  },
  "sorts": [
    {
      "columnName": "lastChangedOn",
      "operation": "Descending"
    },
    {
      "columnName": "entitlementNo",
      "operation": "Ascending"
    },
    {
      "columnName": "customerNameSnapShot",
      "operation": "Ascending"
    },
    {
      "columnName": "commercialProductNameSnapShot",
      "operation": "Ascending"
    },
    {
      "columnName": "entitlementModelNameSnapShot",
      "operation": "Ascending"
    },
    {
      "columnName": "quantity",
      "operation": "Ascending"
    },
    {
      "columnName": "status",
      "operation": "Ascending"
    },
    {
      "columnName": "validFrom",
      "operation": "Ascending"
    },
    {
      "columnName": "validTo",
      "operation": "Ascending"
    }
  ]
};

async function sendRequests() {
  const promises = [];
  for (let i = 0; i < 4; i++) {
    promises.push(
      axios.post(url, data, { headers })
        .then(response => console.log(`Request ${i + 1} succeeded:`, response.data))
        .catch(error => console.error(`Request ${i + 1} failed:`, error.message))
    );
  }
  await Promise.all(promises);
}

sendRequests();
