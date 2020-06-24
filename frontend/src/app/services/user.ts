

export class User{
    constructor(
        public id : number,
        public username : string,
        public name : string,
        public email : string,
        public address : string,
        public enabled : boolean,
        public oglasId : number,
        public authorities:string[]
    ){}
}