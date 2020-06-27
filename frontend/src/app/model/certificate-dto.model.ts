export class CertificateDTO{
    constructor(
        public alias : string,
        public issuedBy : string,
        public issuedTo : string,
        public startDate : string,
        public endDate : string,
        public certificateType : string
    ){}
}