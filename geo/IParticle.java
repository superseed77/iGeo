/*---

    iGeo - http://igeo.jp

    Copyright (c) 2002-2011 Satoru Sugihara

    This file is part of iGeo.

    iGeo is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, version 3.

    iGeo is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with iGeo.  If not, see <http://www.gnu.org/licenses/>.

---*/

package igeo.geo;

import java.awt.Color;

import java.util.ArrayList;

import igeo.core.*;
import igeo.gui.*;

/**
   Class of an implementation of IDynamicObject to have physical attributes of point.
   It has attributes of position, velocity, acceleration, force, and mass.
   Position is provided from outside to be linked.
   
   @author Satoru Sugihara
   @version 0.7.0.0;
*/
public class IParticle extends IDynamicObjectBase implements IParticleI, IVecI{
    
    static double defaultFriction = 0.0;
    
    //public IObject parent=null;
    
    public double mass=1.0;
    public IVec pos;
    public IVec vel;
    //public IVec acc;
    public IVec frc;
    boolean fixed=false;
    public double friction = defaultFriction;
    
    public IParticle(IVec pos){ this.pos = pos; initParticle(); }
    public IParticle(IVec pos, IObject parent){ super(parent); this.pos = pos; initParticle(); }
    
    public IParticle(IVecI p){ pos = p.get(); initParticle(); }
    public IParticle(IVecI p, IObject parent){ super(parent); pos = p.get(); initParticle(); }
    
    public IParticle(double x, double y, double z){ pos = new IVec(x,y,z); initParticle(); }
    public IParticle(double x, double y, double z, IObject parent){ super(parent); pos = new IVec(x,y,z); initParticle(); }
    
    public IParticle(IPoint pt){ super(pt); pos = pt.pos; initParticle(); }
    public IParticle(IPointR pt){ super(pt); pos = pt.pos.get(); initParticle(); }
    
    public IParticle(IParticle ptcl){
	super(ptcl.parent);
	pos = ptcl.pos.dup();
	initParticle();
    }
    
    public IParticle(IParticle ptcl, IObject parent){
	super(parent);
	pos = ptcl.pos.dup();
	initParticle();
    }
    
    synchronized public void initParticle(){
	vel = new IVec();
	//acc = new IVec();
	frc = new IVec();
    }
    
    public IParticle dup(){ return new IParticle(this); }
    // temporary
    //public IParticle dup(){ return this; }
    
    
    // implementation of IDynamicObject
    //public IObject parent(){ return parent; }
    //public ISubobject parent(IObject parent){ this.parent=parent; return this; }
    
    synchronized public IParticle fix(){ fixed=true; return this; }
    synchronized public IParticle unfix(){ fixed=false; return this; }
    
    synchronized public double mass(){ return mass; }
    synchronized public IParticle mass(double mass){ this.mass=mass; return this; }
    
    synchronized public IVec position(){ return pos(); }
    synchronized public IParticle position(IVec v){ pos(v); return this; }
    
    synchronized public IVec pos(){ return pos; }
    synchronized public IParticle pos(IVec v){ pos.set(v); return this; }
    
    synchronized public IVec velocity(){ return vel(); }
    synchronized public IParticle velocity(IVec v){ vel(v); return this; }
    
    synchronized public IVec vel(){ return vel; }
    synchronized public IParticle vel(IVec v){ vel.set(v); return this; }
    
    //synchronized public IVec acceleration(){ return acc(); }
    //synchronized public IParticle acceleration(IVec v){ acc(v); return this; }
    //synchronized public IVec acc(){ return acc; }
    //synchronized public IParticle acc(IVec v){ acc.set(v); return this; }
    
    synchronized public IVec force(){ return frc(); }
    synchronized public IParticle force(IVec v){ frc(v); return this; }
    
    synchronized public IVec frc(){ return frc; }
    synchronized public IParticle frc(IVec v){ frc.set(v); return this; }
    
    synchronized public double friction(){ return fric(); }
    synchronized public IParticle friction(double friction){ fric(friction); return this; }
    
    synchronized public double fric(){ return friction; }
    synchronized public IParticle fric(double friction){ this.friction=friction; return this; }
    
    synchronized public IParticle addForce(IVec f){ frc.add(f); return this; }
    
    synchronized public IParticle resetForce(){ frc.set(0,0,0); return this; }
    
    synchronized public void interact(ArrayList<IDynamicObject> dynamics){}
    
    synchronized public void update(){
	if(fixed) return;
	vel.add(frc.mul(IConfig.dynamicsSpeed/mass)).mul(1.0-friction);
	pos.add(vel.dup().mul(IConfig.dynamicsSpeed));
	frc.set(0,0,0);
	//if(parent!=null) parent.updateGraphic(); // graphic update
	//super.update();
	updateTarget();
    }
    
    
    /************************************************************************
     * implements of IVecI
     ***********************************************************************/
    
    public double x(){ return pos.x(); }
    public double y(){ return pos.y(); }
    public double z(){ return pos.z(); }
    public IVec get(){ return pos.get(); }
    
    public IVec2 to2d(){ return pos.to2d(); }
    public IVec4 to4d(){ return pos.to4d(); }
    public IVec4 to4d(double w){ return pos.to4d(w); }
    public IVec4 to4d(IDoubleI w){ return pos.to4d(w); }
    
    public IDouble getX(){ return pos.getX(); }
    public IDouble getY(){ return pos.getY(); }
    public IDouble getZ(){ return pos.getZ(); }
    
    public IParticle set(IVecI v){ pos.set(v); return this; }
    public IParticle set(double x, double y, double z){ pos.set(x,y,z); return this;}
    public IParticle set(IDoubleI x, IDoubleI y, IDoubleI z){ pos.set(x,y,z); return this; }

    public IParticle add(double x, double y, double z){ pos.add(x,y,z); return this; }
    public IParticle add(IDoubleI x, IDoubleI y, IDoubleI z){ pos.add(x,y,z); return this; }    
    public IParticle add(IVecI v){ pos.add(v); return this; }
    
    public IParticle sub(double x, double y, double z){ pos.sub(x,y,z); return this; }
    public IParticle sub(IDoubleI x, IDoubleI y, IDoubleI z){ pos.sub(x,y,z); return this; }
    public IParticle sub(IVecI v){ pos.sub(v); return this; }
    public IParticle mul(IDoubleI v){ pos.mul(v); return this; }
    public IParticle mul(double v){ pos.mul(v); return this; }
    public IParticle div(IDoubleI v){ pos.div(v); return this; }
    public IParticle div(double v){ pos.div(v); return this; }
    public IParticle neg(){ pos.neg(); return this; }
    public IParticle rev(){ return neg(); }
    public IParticle flip(){ return neg(); }
    
    public IParticle add(IVecI v, double f){ pos.add(v,f); return this; }
    public IParticle add(IVecI v, IDoubleI f){ pos.add(v,f); return this; }
    
    
    public double dot(IVecI v){ return pos.dot(v); }
    public double dot(ISwitchE e, IVecI v){ return pos.dot(e,v); }
    public IDouble dot(ISwitchR r, IVecI v){ return pos.dot(r,v); }
    
    // creating IParticle is too much (in terms of memory occupancy)
    public IVec cross(IVecI v){ return pos.cross(v); }
    //public IParticle cross(IVecI v){ return dup().set(pos.cross(v)); }
    
    public double len(){ return pos.len(); }
    public double len(ISwitchE e){ return pos.len(e); }
    public IDouble len(ISwitchR r){ return pos.len(r); }
    
    public double len2(){ return pos.len2(); }
    public double len2(ISwitchE e){ return pos.len2(e); }
    public IDouble len2(ISwitchR r){ return pos.len2(r); }
    
    public IParticle len(IDoubleI l){ pos.len(l); return this; }
    public IParticle len(double l){ pos.len(l); return this; }
    
    public IParticle unit(){ pos.unit(); return this; }
    
    public double dist(IVecI v){ return pos.dist(v); }
    public double dist(ISwitchE e, IVecI v){ return pos.dist(e,v); }
    public IDouble dist(ISwitchR r, IVecI v){ return pos.dist(r,v); }
    
    public double dist2(IVecI v){ return pos.dist2(v); }
    public double dist2(ISwitchE e, IVecI v){ return pos.dist2(e,v); }
    public IDouble dist2(ISwitchR r, IVecI v){ return pos.dist2(r,v); }
    
    public boolean eq(IVecI v){ return pos.eq(v); }
    public boolean eq(ISwitchE e, IVecI v){ return pos.eq(e,v); }
    public IBool eq(ISwitchR r, IVecI v){ return pos.eq(r,v); }
    
    public boolean eq(IVecI v, double resolution){ return pos.eq(v,resolution); }
    public boolean eq(ISwitchE e, IVecI v, double resolution){ return pos.eq(e,v,resolution); }
    public IBool eq(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eq(r,v,resolution); }
    
    public boolean eqX(IVecI v){ return pos.eqX(v); }
    public boolean eqY(IVecI v){ return pos.eqY(v); }
    public boolean eqZ(IVecI v){ return pos.eqZ(v); }
    public boolean eqX(ISwitchE e, IVecI v){ return pos.eqX(e,v); }
    public boolean eqY(ISwitchE e, IVecI v){ return pos.eqY(e,v); }
    public boolean eqZ(ISwitchE e, IVecI v){ return pos.eqZ(e,v); }
    public IBool eqX(ISwitchR r, IVecI v){ return pos.eqX(r,v); }
    public IBool eqY(ISwitchR r, IVecI v){ return pos.eqY(r,v); }
    public IBool eqZ(ISwitchR r, IVecI v){ return pos.eqZ(r,v); }
    
    public boolean eqX(IVecI v, double resolution){ return pos.eqX(v,resolution); }
    public boolean eqY(IVecI v, double resolution){ return pos.eqY(v,resolution); }
    public boolean eqZ(IVecI v, double resolution){ return pos.eqZ(v,resolution); }
    public boolean eqX(ISwitchE e, IVecI v, double resolution){ return pos.eqX(e,v,resolution); }
    public boolean eqY(ISwitchE e, IVecI v, double resolution){ return pos.eqY(e,v,resolution); }
    public boolean eqZ(ISwitchE e, IVecI v, double resolution){ return pos.eqZ(e,v,resolution); }
    public IBool eqX(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eqX(r,v,resolution); }
    public IBool eqY(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eqY(r,v,resolution); }
    public IBool eqZ(ISwitchR r, IVecI v, IDoubleI resolution){ return pos.eqZ(r,v,resolution); }
    
    
    public double angle(IVecI v){ return pos.angle(v); }
    public double angle(ISwitchE e, IVecI v){ return pos.angle(e,v); }
    public IDouble angle(ISwitchR r, IVecI v){ return pos.angle(r,v); }
    
    public double angle(IVecI v, IVecI axis){ return pos.angle(v,axis); }
    public double angle(ISwitchE e, IVecI v, IVecI axis){ return pos.angle(e,v,axis); }
    public IDouble angle(ISwitchR r, IVecI v, IVecI axis){ return pos.angle(r,v,axis); }
    
    public IParticle rot(IVecI axis, IDoubleI angle){ pos.rot(axis,angle); return this; }
    public IParticle rot(IVecI axis, double angle){ pos.rot(axis,angle); return this; }
    
    public IParticle rot(IVecI center, IVecI axis, double angle){
	pos.rot(center, axis,angle); return this;
    }
    public IParticle rot(IVecI center, IVecI axis, IDoubleI angle){
	pos.rot(center, axis,angle); return this;
    }
    
    
    /**
       Rotate to destination direction vector.
    */
    public IParticle rot(IVecI axis, IVecI destDir){ pos.rot(axis,destDir); return this; }
    /**
       Rotate to destination point location.
    */
    public IParticle rot(IVecI center, IVecI axis, IVecI destPt){
	pos.rot(center,axis,destPt); return this;
    }
    
    
    /** alias of mul */
    public IParticle scale(IDoubleI f){ pos.scale(f); return this; }
    /** alias of mul */
    public IParticle scale(double f){ pos.scale(f); return this; }
    
    public IParticle scale(IVecI center, IDoubleI f){ pos.scale(center,f); return this; }
    public IParticle scale(IVecI center, double f){ pos.scale(center,f); return this; }
    
    /** scale only in 1 direction */
    public IParticle scale1d(IVecI axis, double f){ pos.scale1d(axis,f); return this; }
    public IParticle scale1d(IVecI axis, IDoubleI f){ pos.scale1d(axis,f); return this; }
    public IParticle scale1d(IVecI center, IVecI axis, double f){
	pos.scale1d(center,axis,f); return this;
    }
    public IParticle scale1d(IVecI center, IVecI axis, IDoubleI f){
	pos.scale1d(center,axis,f); return this;
    }
    
    
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle ref(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle ref(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle mirror(IVecI planeDir){ pos.ref(planeDir); return this; }
    /** reflect (mirror) 3 dimensionally to the other side of the plane */
    public IParticle mirror(IVecI center, IVecI planeDir){
	pos.ref(center,planeDir); return this;
    }
    
    /** shear operation */
    public IParticle shear(double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IVecI center, double sxy, double syx, double syz,
			double szy, double szx, double sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    public IParticle shear(IVecI center, IDoubleI sxy, IDoubleI syx, IDoubleI syz,
			IDoubleI szy, IDoubleI szx, IDoubleI sxz){
	pos.shear(center,sxy,syx,syz,szy,szx,sxz); return this;
    }
    
    public IParticle shearXY(double sxy, double syx){ pos.shearXY(sxy,syx); return this; }
    public IParticle shearXY(IDoubleI sxy, IDoubleI syx){ pos.shearXY(sxy,syx); return this; }
    public IParticle shearXY(IVecI center, double sxy, double syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    public IParticle shearXY(IVecI center, IDoubleI sxy, IDoubleI syx){
	pos.shearXY(center,sxy,syx); return this;
    }
    
    public IParticle shearYZ(double syz, double szy){ pos.shearYZ(syz,szy); return this; }
    public IParticle shearYZ(IDoubleI syz, IDoubleI szy){ pos.shearYZ(syz,szy); return this; }
    public IParticle shearYZ(IVecI center, double syz, double szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    public IParticle shearYZ(IVecI center, IDoubleI syz, IDoubleI szy){
	pos.shearYZ(center,syz,szy); return this;
    }
    
    public IParticle shearZX(double szx, double sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticle shearZX(IDoubleI szx, IDoubleI sxz){ pos.shearZX(szx,sxz); return this; }
    public IParticle shearZX(IVecI center, double szx, double sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    public IParticle shearZX(IVecI center, IDoubleI szx, IDoubleI sxz){
	pos.shearZX(center,szx,sxz); return this;
    }
    
    /** translate is alias of add() */
    public IParticle translate(double x, double y, double z){ pos.translate(x,y,z); return this; }
    public IParticle translate(IDoubleI x, IDoubleI y, IDoubleI z){ pos.translate(x,y,z); return this; }
    public IParticle translate(IVecI v){ pos.translate(v); return this; }
    
    
    public IParticle transform(IMatrix3I mat){ pos.transform(mat); return this; }
    public IParticle transform(IMatrix4I mat){ pos.transform(mat); return this; }
    public IParticle transform(IVecI xvec, IVecI yvec, IVecI zvec){
	pos.transform(xvec,yvec,zvec); return this;
    }
    public IParticle transform(IVecI xvec, IVecI yvec, IVecI zvec, IVecI translate){
	pos.transform(xvec,yvec,zvec,translate); return this;
    }
    
    
    /** mv() is alias of add() */
    public IParticle mv(double x, double y, double z){ return add(x,y,z); }
    public IParticle mv(IDoubleI x, IDoubleI y, IDoubleI z){ return add(x,y,z); }
    public IParticle mv(IVecI v){ return add(v); }
    
    // method name cp() is used as getting control point method in curve and surface but here used also as copy because of the priority of variable fitting of diversed users' mind set over the clarity of the code organization
    /** cp() is alias of dup() */ 
    public IParticle cp(){ return dup(); }
    
    /** cp() is alias of dup().add() */
    public IParticle cp(double x, double y, double z){ return dup().add(x,y,z); }
    public IParticle cp(IDoubleI x, IDoubleI y, IDoubleI z){ return dup().add(x,y,z); }
    public IParticle cp(IVecI v){ return dup().add(v); }
    
    
    
    // methods creating new instance // returns IParticle?, not IVec?
    // returns IVec, not IParticle (2011/10/12)
    //public IParticle diff(IVecI v){ return dup().sub(v); }
    public IVec diff(IVecI v){ return pos.diff(v); }
    //public IParticle mid(IVecI v){ return dup().add(v).div(2); }
    public IVec mid(IVecI v){ return pos.mid(v); }
    //public IParticle sum(IVecI v){ return dup().add(v); }
    public IVec sum(IVecI v){ return pos.sum(v); }
    //public IParticle sum(IVecI... v){ IParticle ret = this.dup(); for(IVecI vi: v) ret.add(vi); return ret; }
    public IVec sum(IVecI... v){ return pos.sum(v); }
    //public IParticle bisect(IVecI v){ return dup().unit().add(v.dup().unit()); }
    public IVec bisect(IVecI v){ return pos.bisect(v); }
    
    
    
    /**
       weighted sum.
       @return IVec
    */
    //public IParticle sum(IVecI v2, double w1, double w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVecI v2, double w1, double w2){ return pos.sum(v2,w1,w2); }
    //public IParticle sum(IVecI v2, double w2){ return dup().mul(1.0-w2).add(v2,w2); }
    public IVec sum(IVecI v2, double w2){ return pos.sum(v2,w2); }
    
    
    //public IParticle sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return dup().mul(w1).add(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w1, IDoubleI w2){ return sum(v2,w1,w2); }
    
    //public IParticle sum(IVecI v2, IDoubleI w2){ return dup().mul(new IDouble(1.0).sub(w2)).add(v2,w2); }
    public IVec sum(IVecI v2, IDoubleI w2){ return sum(v2,w2); }
    
    
    /** alias of cross. (not unitized ... ?) */
    //public IParticle nml(IVecI v){ return cross(v); }
    public IVec nml(IVecI v){ return pos.nml(v); }
    /** create normal vector from 3 points of self, pt1 and pt2 */
    //public IParticle nml(IVecI pt1, IVecI pt2){ return this.diff(pt1).cross(this.diff(pt2)).unit(); }
    public IVec nml(IVecI pt1, IVecI pt2){ return pos.nml(pt1,pt2); }
    
    
    /** checking x, y, and z is valid number (not Infinite, nor NaN). */
    public boolean isValid(){ return pos.isValid(); }
    
    
    public String toString(){ return pos.toString(); }
    
    
}
