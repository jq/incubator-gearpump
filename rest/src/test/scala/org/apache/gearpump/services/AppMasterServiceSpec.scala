/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.gearpump.services

import org.apache.gearpump.cluster.AppMasterInfo
import org.apache.gearpump.cluster.MasterToAppMaster.AppMasterData
import org.apache.gearpump.services.RestTestUtil.RestTest
import org.specs2.mutable.Specification
import org.specs2.specification.{BeforeExample, AfterExample}
import spray.testkit.Specs2RouteTest

class AppMasterServiceSpec extends Specification with Specs2RouteTest
with AppMasterService with AfterExample with BeforeExample {
  import org.apache.gearpump.services.AppMasterProtocol._
  import spray.httpx.SprayJsonSupport._
  def actorRefFactory = system
  Thread.sleep(500)
  val restUtil = before
  val master = restUtil.miniCluster.mockMaster

  def before: RestTest = {
    RestTestUtil.startRestServices
  }

  "AppMasterService" should {
//    "return a JSON structure for GET request when detail = true" in {
//      Get("/appmaster/0?detail=true") ~> routes ~> check {
//        responseAs[AppMasterDataDetail] === AppMasterDataDetail(0, AppDescription("test", classOf[AppMaster].getCanonicalName, Configs.empty, Graph.empty))
//      }
//    }
    "return a JSON structure for GET request when detail = false" in {
      Get("/appmaster/0?detail=false") ~> routes ~> check {
        responseAs[AppMasterData] === AppMasterData(0, AppMasterInfo(null))
      }
    }
  }

  def after: Unit = {
    restUtil.shutdown()
  }

}