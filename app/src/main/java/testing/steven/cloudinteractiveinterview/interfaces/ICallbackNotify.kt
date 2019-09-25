package testing.steven.cloudinteractiveinterview.interfaces

interface ICallbackNotify<in T> {
     fun dataFetched(data:Any?)
     fun failure()

}