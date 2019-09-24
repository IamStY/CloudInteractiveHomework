package testing.steven.cloudinteractiveinterview.api

interface ICallbackNotify<in T> {
     fun dataFetched(data:Any?)
     fun failure()

}